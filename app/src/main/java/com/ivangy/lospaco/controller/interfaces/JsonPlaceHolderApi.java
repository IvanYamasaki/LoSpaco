package com.ivangy.lospaco.controller.interfaces;


import com.ivangy.lospaco.model.Account;
import com.ivangy.lospaco.model.Cart;
import com.ivangy.lospaco.model.Category;
import com.ivangy.lospaco.model.JsonResponse;
import com.ivangy.lospaco.model.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("Category/GetList")
    Call<List<Category>> getAllCategories();

    @GET("Service/GetByCategoryId/{id}")
    Call<List<Service>> getServicesByCategory(@Path("id") int id);

    @GET("Service/GetByName/{name}")
    Call<Service> getServiceByName(@Query("name") String name);

    @POST("Account/Insert")
    Call<JsonResponse> postAccount(@Query("email") String email, @Query("password") String password);

    @GET("Account/GetByEmail/{email}")
    Call<Account> getAccount(@Query("email") String email);

    @POST("Account/Login")
    Call<Boolean> isLoginValid(@Query("email") String email, @Query("password") String password);

    @GET("Customer/GetById/{id}")
    Call<Boolean> getCustomerById(@Path("id") int id);

    @GET("Cart/GetList/{id}")
    Call<List<Cart>> getCartById(@Path("id") int id);

    @GET("Cart/GetTotalPrice/{id}")
    Call<Double> getCarTotalPrice(@Path("id") int id);

}
