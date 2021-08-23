package com.example.pilreminderapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String base_url = "https://pilreminderapp.herokuapp.com/";

    retrofit2.Retrofit retrofit=new retrofit2.Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public LaravelApi laravelApi=retrofit.create(LaravelApi.class);
}