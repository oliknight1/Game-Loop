package com.example.gameloop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.gameloop.models.ApiResponse;
import com.example.gameloop.models.Game;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String baseUrl = "https://api.rawg.io/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RAWGApi rawgApi = retrofit.create(RAWGApi.class);

        recyclerView = findViewById(R.id.recyclerView);
        Call<ApiResponse> call = rawgApi.getAllGames();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if( !response.isSuccessful() ) {
                    // Handle Error
                    return;
                }
//                setAdapter(games);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("FAIL", "Failed: " + t.getMessage());
            }
        });
    }

    private void setAdapter( List<Game> gameList) {
        RecyclerAdapter adapter = new RecyclerAdapter(gameList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}