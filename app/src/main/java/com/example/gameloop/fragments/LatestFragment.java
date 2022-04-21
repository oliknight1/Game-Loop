package com.example.gameloop.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gameloop.BuildConfig;
import com.example.gameloop.R;
import com.example.gameloop.RAWGApi;
import com.example.gameloop.RecyclerAdapter;
import com.example.gameloop.controllers.GameController;
import com.example.gameloop.controllers.GameControllerCallback;
import com.example.gameloop.models.ApiResponse;
import com.example.gameloop.models.Game;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LatestFragment extends Fragment {
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;

    int currentPage = 1;
    public LatestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Latest");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.latestRecyclerView);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        recyclerView = view.findViewById(R.id.latestRecyclerView);

        shimmerFrameLayout = view.findViewById(R.id.latestShimmerLayout);
        shimmerFrameLayout.startShimmer();

        RecyclerAdapter adapter = new RecyclerAdapter(new ArrayList<>());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        GameController gameController = new GameController();
        gameController.getLatest( currentPage, new GameControllerCallback() {
            @Override
            public void onSuccess(List<Game> result) {
                adapter.setGameList(result);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                boolean loadMore = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition() == adapter.getItemCount() -1;
                if (loadMore) loadMoreItems();

            }
            private void loadMoreItems() {
                currentPage++;
                gameController.getLatest(currentPage,new GameControllerCallback() {
                    @Override
                    public void onSuccess(List<Game> result) {
                        adapter.addAll(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });

        return view;
    }
}