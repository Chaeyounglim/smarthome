package com.example.smarthome0807;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("/api/main/getDatas")
    Call<Map<String,Float>> getPosts();
}