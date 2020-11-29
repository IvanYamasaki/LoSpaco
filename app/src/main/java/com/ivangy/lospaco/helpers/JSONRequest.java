package com.ivangy.lospaco.helpers;

import android.content.Context;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.interfaces.JsonPlaceHolderApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class JSONRequest <T>{


    public JSONRequest(Call<T> call, Method method) {
        requestJSON(call, method);
    }

    public static JsonPlaceHolderApi getJsonPlaceHolderApi(Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.base_url_web_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(JsonPlaceHolderApi.class);
    }

    public <T> void requestJSON(Call<T> call, Method method){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                method.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                method.onFailure(call, t);
            }
        });

    }

}
