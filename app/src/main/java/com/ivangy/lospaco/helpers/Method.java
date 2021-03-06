package com.ivangy.lospaco.helpers;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Response;

public abstract class Method {

    public abstract <T> void onResponse(Call<T> call, Response<T> response) throws UnsupportedEncodingException;
    public abstract <T> void onFailure(Call<T> call, Throwable t);

}
