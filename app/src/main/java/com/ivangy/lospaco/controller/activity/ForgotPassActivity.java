package com.ivangy.lospaco.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.ivangy.lospaco.R;

import java.util.Objects;

import static com.ivangy.lospaco.helpers.AndroidHelper.get;

public class ForgotPassActivity extends AppCompatActivity {
  TextInputEditText txtEmail;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_pass);

    txtEmail = findViewById(R.id.txtEmailCust);
    txtEmail.setText(Objects.requireNonNull(getIntent().getExtras()).getString("email"));

  }

  public void backLogin(View view){
    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    intent.putExtra("email", get(txtEmail));
    startActivity(intent);
  }

}