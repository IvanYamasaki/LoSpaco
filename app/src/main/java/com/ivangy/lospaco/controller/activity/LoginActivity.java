package com.ivangy.lospaco.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.interfaces.JsonPlaceHolderApi;
import com.ivangy.lospaco.helpers.JSONRequest;
import com.ivangy.lospaco.helpers.Method;
import com.ivangy.lospaco.model.Account;
import com.ivangy.lospaco.model.Customer;
import com.ivangy.lospaco.model.JsonResponse;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ivangy.lospaco.helpers.AndroidHelper.get;
import static com.ivangy.lospaco.helpers.AndroidHelper.isEmpty;
import static com.ivangy.lospaco.helpers.AndroidHelper.setRecyclerConfig;
import static com.ivangy.lospaco.helpers.AndroidHelper.toastShort;

public class LoginActivity extends AppCompatActivity {

    private ConstraintLayout layoutImgs, layoutComps;
    private TextInputEditText txtEmail, txtPass;
    private ImageView imgLogo;
    private Animation animGrow;
    private ProgressBar pgbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layoutImgs = findViewById(R.id.layoutImgs);
        layoutComps = findViewById(R.id.layoutComps);
        pgbLogin = findViewById(R.id.pgbLogin);

        layoutImgs.setAnimation(AnimationUtils.loadAnimation(this, R.anim.uptodown));
        layoutComps.setAnimation(AnimationUtils.loadAnimation(this, R.anim.downtoup));

        txtEmail = findViewById(R.id.txtLogin);
        txtPass = findViewById(R.id.txtPassword);
        imgLogo = findViewById(R.id.imgLogo);

        if (getIntent().getExtras() != null)
            txtEmail.setText(Objects.requireNonNull(getIntent().getExtras()).getString("email"));

        animGrow = AnimationUtils.loadAnimation(this, R.anim.small_grow);

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void login(View view) {
        pgbLogin.setVisibility(View.VISIBLE);
        if (isEmpty(txtEmail) || isEmpty(txtPass)) {
            toastShort(getApplicationContext(), "Campos Obrigatórios");
        } else {
            verifyLogin(get(txtEmail), get(txtPass));
        }
    }

    public void forgotPass(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgotPassActivity.class);
        intent.putExtra("email", get(txtEmail));
        startActivity(intent);
    }

    public void createAccount(View view) {
        startActivity(new Intent(this, CreateAccountActivity.class));
    }

    public void clickLogo(View view) {
        imgLogo.startAnimation(animGrow);
    }

    private void verifyLogin(String email, String password) {

        new JSONRequest(
                JSONRequest.getJsonPlaceHolderApi(this).isLoginValid(email, password),
                new Method() {
                    @Override
                    public <T> void onResponse(Call<T> call, Response<T> response) {
                        pgbLogin.setVisibility(View.GONE);
                        if (!response.isSuccessful()) {
                            toastShort(LoginActivity.this, "Código do erro: " + response.code());
                            return;
                        }
                        Boolean jResponse = (Boolean) response.body();

                        if (jResponse) {
                            onLoginSuccess();
                            saveLogin(email);
                        } else {
                            onLoginFail();
                        }
                    }

                    @Override
                    public <T> void onFailure(Call<T> call, Throwable t) {
                        toastShort(LoginActivity.this, "" + t.getMessage() +
                                "\n Houve um problema ao verificar os dados, por favor, tente novamente");
                    Log.d("Error_Message", t.getMessage());
                    }
                });
    }

    public void onLoginSuccess() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void saveLogin(String email) {

        new JSONRequest(
                JSONRequest.getJsonPlaceHolderApi(this).getAccount(email),
                new Method() {
                    @Override
                    public <T> void onResponse(Call<T> call, Response<T> response) {
                        if (!response.isSuccessful()) {
                            toastShort(LoginActivity.this, "Código do erro: " + response.code());
                            return;
                        }
                        Account jResponse = (Account) response.body();

                        FileOutputStream fos;
                        try {
                            fos = new FileOutputStream(getFileStreamPath(getResources().getString(R.string.fos_account)));
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            oos.writeObject(jResponse);
                            oos.close();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public <T> void onFailure(Call<T> call, Throwable t) {
                        toastShort(LoginActivity.this, "" + t.getMessage() +
                                "\n Houve um problema ao verificar os dados, por favor, tente novamente");
                    }
                });
    }

    public void onLoginFail() {
        toastShort(this, "Email ou Senha incorretos");
    }


}