package com.ivangy.lospaco.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.ivangy.lospaco.R;

import java.util.Objects;

import static com.ivangy.lospaco.helpers.AndroidHelper.get;
import static com.ivangy.lospaco.helpers.AndroidHelper.isEmpty;
import static com.ivangy.lospaco.helpers.AndroidHelper.toastShort;

public class LoginActivity extends AppCompatActivity {

  ConstraintLayout layoutImgs, layoutComps;
  TextInputEditText txtEmail, txtPass;
  ImageView imgLogo;
  Animation animGrow;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    layoutImgs = findViewById(R.id.layoutImgs);
    layoutComps = findViewById(R.id.layoutComps);

    layoutImgs.setAnimation(AnimationUtils.loadAnimation(this, R.anim.uptodown));
    layoutComps.setAnimation(AnimationUtils.loadAnimation(this, R.anim.downtoup));

    txtEmail = findViewById(R.id.txtLogin);
    txtPass = findViewById(R.id.txtPassword);
    imgLogo = findViewById(R.id.imgLogo);

    if(getIntent().getExtras()!=null)
      txtEmail.setText(Objects.requireNonNull(getIntent().getExtras()).getString("email"));

    animGrow = AnimationUtils.loadAnimation(this, R.anim.small_grow);
  }

  public void login(View view){
    Intent intent = new Intent(this, MainActivity.class);
    intent.putExtra("login", 1);
    startActivity(intent);
    finish();
  }

  public void forgotPass(View view){
    Intent intent = new Intent(getApplicationContext(), ForgotPassActivity.class);
    intent.putExtra("email", get(txtEmail));
    startActivity(intent);
  }

  public void createAccount(View view){
    if(isEmpty(txtEmail)||isEmpty(txtPass)){
      toastShort(getApplicationContext(), "Campos Obrigatórios");
    }else{
      Intent intent = new Intent(getApplicationContext(), CreateAccountPassActivity.class);
      intent.putExtra("email", get(txtEmail));
      intent.putExtra("pass", get(txtPass));
      startActivity(intent);
    }
  }

  public void clickLogo(View view){
    imgLogo.startAnimation(animGrow);
  }

}