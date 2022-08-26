package com.farasatnovruzov.newsappjava.service;


import com.farasatnovruzov.newsappjava.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private NewsAPI newsAPI;
    private static RetrofitInstance INSTANCE;

    public static RetrofitInstance getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RetrofitInstance();
        }
        return INSTANCE;
    }

    private RetrofitInstance() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        newsAPI = retrofit.create(NewsAPI.class);
    }
}
