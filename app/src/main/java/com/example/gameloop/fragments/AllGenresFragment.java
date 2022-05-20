package com.example.gameloop.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gameloop.R;
import com.example.gameloop.adapters.GenreAdapter;
import com.example.gameloop.controllers.GenreListCallback;
import com.example.gameloop.controllers.RequestController;
import com.example.gameloop.models.Genre;

import java.util.ArrayList;
import java.util.List;

public class AllGenresFragment extends Fragment {
    FragmentActivity fragmentActivity;
    private RecyclerView recyclerView;

    public AllGenresFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentActivity = getActivity();
        fragmentActivity.setTitle("All Genres");
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.genresRecyclerView);
        recyclerView.setItemAnimator(null);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_genres, container, false);
        recyclerView = view.findViewById(R.id.genresRecyclerView);

        GenreAdapter adapter = new GenreAdapter(new ArrayList<>());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        GenreListCallback callback = new GenreListCallback() {
            @Override
            public void onSuccess(List<Genre> result) {
                adapter.setGenreList(result);
                recyclerView.setVisibility(View.VISIBLE);
            }

            public void onFailure(Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        };

        RequestController requestController = new RequestController();
        requestController.getAllGenres(callback);
        return view;
    }
}