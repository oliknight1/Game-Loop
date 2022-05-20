package com.example.gameloop;

import com.example.gameloop.models.ApiResponse;
import com.example.gameloop.models.Game;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RAWGApi {
    @GET("games?&key=" + BuildConfig.API_KEY)
    Call<ApiResponse> getAllGames();

    @GET("games?&key=" + BuildConfig.API_KEY)
    Call<ApiResponse> getLatestGames(
            @Query("dates") String dateRange,
            @Query("ordering") String ordering,
            @Query("page") int page,
            @Query("page_size") int pageSize
    );

    @GET("games/{id}?key=" + BuildConfig.API_KEY)
    Call<Game> getData(
            @Path("id") int id
    );
    @GET("games?&key=" + BuildConfig.API_KEY)
    Call<ApiResponse> getPopularGames(
            @Query("dates") String dateRange,
            @Query("ordering") String ordering,
            @Query("page") int page,
            @Query("page_size") int pageSize
    );

    @GET("genres?&key=" + BuildConfig.API_KEY)
    Call<ApiResponse> getAllGenres();

}
