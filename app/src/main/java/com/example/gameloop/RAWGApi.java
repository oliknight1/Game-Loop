package com.example.gameloop;

import com.example.gameloop.models.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RAWGApi {
    @GET("games?&key=" + BuildConfig.API_KEY)
    Call<ApiResponse> getAllGames();

    @GET("games?&key=" + BuildConfig.API_KEY)
    Call<ApiResponse> getLatestGames(@Query("dates") String dateRange, @Query("ordering") String ordering);
}
