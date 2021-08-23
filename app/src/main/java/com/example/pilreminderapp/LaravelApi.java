package com.example.pilreminderapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LaravelApi {

    @GET("api/faq")
    Call<List<Faq>> getFaq();

}
