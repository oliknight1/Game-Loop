package com.example.gameloop.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gameloop.R;
import com.example.gameloop.RAWGApi;
import com.example.gameloop.RecyclerAdapter;
import com.example.gameloop.models.ApiResponse;
import com.example.gameloop.models.Game;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LatestFragment extends Fragment {
    private RecyclerView recyclerView;
    public LatestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Latest");



    }

    private void setAdapter( List<Game> gameList, View view) {
        RecyclerAdapter adapter = new RecyclerAdapter(gameList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        recyclerView = view.findViewById(R.id.latestRecyclerView);
        String baseUrl = "https://api.rawg.io/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RAWGApi rawgApi = retrofit.create(RAWGApi.class);

        Call<ApiResponse> call = rawgApi.getAllGames();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if( !response.isSuccessful() ) {
                    // Handle Error
                    return;
                }
                List<Game> games = Arrays.asList(response.body().getResults());
                setAdapter(games, view);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("FAIL", "Failed: " + t.getMessage());
            }
        });
        return view;
    }
}