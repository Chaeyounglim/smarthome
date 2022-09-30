package com.example.smarthome0807.Retrofit2;

import com.example.smarthome0807.Interface.JsonPlaceHolderApi;
import com.example.smarthome0807.Interface.PostApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    String url = "http://10.0.2.2:8081";

    Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    PostApi postApi = retrofit2.create(PostApi.class);
    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit2.create(JsonPlaceHolderApi.class);
}
