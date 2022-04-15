package com.example.gameloop;

import com.example.gameloop.models.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RAWGApi {
    @GET("games?&key=" + BuildConfig.API_KEY)
    Call<ApiResponse> getAllGames();

    @GET()
    Call<ApiResponse> getLatestGames(@Url String url);
}
