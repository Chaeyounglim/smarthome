package com.example.smarthome0807;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserData {
    @GET("/api/users/getUsers")
    Call<Map<String,String>> getPosts();
}
