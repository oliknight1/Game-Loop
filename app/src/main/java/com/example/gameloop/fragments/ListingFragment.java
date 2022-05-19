package com.example.gameloop.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameloop.R;
import com.example.gameloop.RecyclerAdapter;
import com.example.gameloop.controllers.GameController;
import com.example.gameloop.controllers.GameListCallback;
import com.example.gameloop.models.Game;
import com.example.gameloop.models.PageType;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class ListingFragment extends Fragment {
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private static final String ARG_ID = "argListingType";
    private PageType pageType;

    int currentPage;
    final int MAX_ITEMS = 100;
    public ListingFragment() {
        // Required empty public constructor
    }

    public static ListingFragment newInstance(PageType type) {
        ListingFragment fragment = new ListingFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ID, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            pageType = (PageType) getArguments().getSerializable(ARG_ID);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.listingRecyclerView);
        recyclerView.setItemAnimator(null);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listing, container, false);
        recyclerView = view.findViewById(R.id.listingRecyclerView);

        currentPage = 1;
        shimmerFrameLayout = view.findViewById(R.id.listingShimmerLayout);
        shimmerFrameLayout.startShimmer();

        RecyclerAdapter adapter = new RecyclerAdapter(new ArrayList<>());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        GameListCallback callback = new GameListCallback() {
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
        };
        GameController gameController = new GameController();
        switch (pageType) {
            case LATEST: {
                gameController.getLatest(currentPage, callback);
                getActivity().setTitle("Latest");
                break;
            }
            case POPULAR:{
                gameController.getPopular(currentPage, callback);
                getActivity().setTitle("Most Popular");
            }
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                boolean loadMore = (((GridLayoutManager) layoutManager).findLastVisibleItemPosition() == adapter.getItemCount() -1) && adapter.getItemCount() <= MAX_ITEMS;
                if (loadMore) loadMoreItems();

            }
            private void loadMoreItems() {
                currentPage++;
                gameController.getLatest(currentPage,new GameListCallback() {
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