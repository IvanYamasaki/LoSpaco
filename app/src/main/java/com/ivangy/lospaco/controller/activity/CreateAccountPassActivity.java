package com.ivangy.lospaco.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.ivangy.lospaco.R;

import java.util.Objects;

import static com.ivangy.lospaco.helpers.AndroidHelper.get;
import static com.ivangy.lospaco.helpers.AndroidHelper.isEmpty;
import static com.ivangy.lospaco.helpers.AndroidHelper.toastShort;

public class CreateAccountPassActivity extends AppCompatActivity {
  Bundle bundle = getIntent().getExtras();
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_account_pass);
  }


  public void next(View view) {
    assert bundle != null;
    TextInputEditText txtPass = findViewById(R.id.txtPass);

    if (!isEmpty(txtPass)) {
      if( Objects.equals(bundle.getString("pass"), get(txtPass))) {
        Intent intent = new Intent(getApplicationContext(), CreateAccountPassActivity.class);

        intent.putExtra("email", bundle.getString("email"));
        intent.putExtra("pass", bundle.getString("pass"));
        startActivity(intent);
      }else toastShort(getApplicationContext(), "Senhas Diferentes");
    }else toastShort(getApplicationContext(), "Campo Obrigatório");
  }
}