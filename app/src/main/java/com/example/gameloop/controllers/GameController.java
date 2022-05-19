package com.example.gameloop.controllers;


import static java.time.temporal.TemporalAdjusters.firstDayOfYear;

import android.util.Log;

import com.example.gameloop.RAWGApi;
import com.example.gameloop.models.ApiResponse;
import com.example.gameloop.models.Game;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameController {
    private Gson gson;
    String baseUrl;
    Retrofit retrofit;
    RAWGApi rawgApi;

    public GameController() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        baseUrl = "https://api.rawg.io/api/";
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        rawgApi = retrofit.create(RAWGApi.class);
    }

    public void getLatest(int page, GameListCallback callback) {
        LocalDate startDate = LocalDate.now().minusMonths(3);
        LocalDate endDate = LocalDate.now();
        String dateRange = startDate.toString() + "," + endDate.toString();
        Call<ApiResponse> call = rawgApi.getLatestGames(dateRange, "-added", page, 8);
        executeRequest(call,callback);
    }

    public void getPopular(int page, GameListCallback callback) {
        LocalDate startDate = LocalDate.now().with(firstDayOfYear());
        LocalDate endDate = LocalDate.now();
        String dateRange = startDate.toString() + "," + endDate.toString();
        Call<ApiResponse> call = rawgApi.getPopularGames(dateRange, "-added", page, 8);
        executeRequest(call,callback);
    }

    public void executeRequest(Call<ApiResponse> call, GameListCallback callback) {
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if( !response.isSuccessful() ) {
                    return;
                }
                Type gameListType = new TypeToken<List<Game>>() {}.getType();
                List<Game> gameList = new Gson().fromJson(response.body().getResults(), gameListType );
                callback.onSuccess(gameList);
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });

    }

    public void getGameData(int id, SingleGameCallback callback) {
        Call<Game> call =rawgApi.getData(id);
        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if( !response.isSuccessful() ) {
                    return;
                }
                Game game = response.body();
                callback.onSuccess(game);
            }
            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                callback.onFailure(t);
            }
        });

    }

}
