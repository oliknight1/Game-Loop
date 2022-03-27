package com.example.gameloop;

import com.example.gameloop.models.ApiResponse;
import com.example.gameloop.models.Game;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RAWGApi {
    @GET("games?&key=" + BuildConfig.API_KEY)
    Call<ApiResponse> getAllGames();
}
