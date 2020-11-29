package com.ivangy.lospaco.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.interfaces.JsonPlaceHolderApi;
import com.ivangy.lospaco.model.JsonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ivangy.lospaco.helpers.AndroidHelper.get;
import static com.ivangy.lospaco.helpers.AndroidHelper.isEmpty;
import static com.ivangy.lospaco.helpers.AndroidHelper.toastShort;

public class CreateAccountActivity extends AppCompatActivity {

    private TextInputEditText txtEmail, txtPass, txtConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        txtEmail = findViewById(R.id.txtEmailCust);
        txtPass = findViewById(R.id.txtPass);
        txtConfirmPass = findViewById(R.id.txtConfirmPass);
        Button btnCreateAcc = findViewById(R.id.btnCreateAcc);

        btnCreateAcc.setOnClickListener(v -> {
            if (isEmpty(txtEmail) || isEmpty(txtPass) || isEmpty(txtConfirmPass)) {
                toastShort(getApplicationContext(), "Campos Obrigatórios");
            } else if (!get(txtPass).equals(get(txtConfirmPass))) {
                toastShort(this, "As senhas não coincidem!");
            } else {
                insertAccount(get(txtEmail), get(txtPass));
            }
        });
    }

    public void insertAccount(String email, String pass){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url_web_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<JsonResponse> call = jsonPlaceHolderApi.postAccount(email, pass);

        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(@NonNull Call<JsonResponse> call, @NonNull Response<JsonResponse> response) {
                if (!response.isSuccessful()){
                    toastShort(CreateAccountActivity.this, "Código do erro: " + response.code());
                    return;
                }
                JsonResponse jResponse = response.body();
                assert jResponse != null;
                if(jResponse.getType()==0){
                    toastShort(CreateAccountActivity.this, jResponse.getValue());
                    Intent i = new Intent(CreateAccountActivity.this, MainActivity.class);
                    i.putExtra("Email", email);
                    startActivity(i);
                }else if(jResponse.getType()==2){
                    toastShort(CreateAccountActivity.this, jResponse.getValue());
                    txtPass.setText("");
                    txtConfirmPass.setText("");
                }
            }
            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                toastShort(CreateAccountActivity.this, "" + t.getMessage() +
                        "\n Houve um problema ao registrar sua conta, por favor, tente novamente");
            }
        });
    }
}