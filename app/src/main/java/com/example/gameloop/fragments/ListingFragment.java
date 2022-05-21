package com.example.gameloop.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameloop.R;
import com.example.gameloop.adapters.GameAdapter;
import com.example.gameloop.controllers.RequestController;
import com.example.gameloop.controllers.RequestCallback;
import com.example.gameloop.models.Game;
import com.example.gameloop.models.PageType;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class ListingFragment extends Fragment {
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private static final String ARG_LISTING_TYPE = "argListingType";
    private static final String ARG_GENRE_ID = "argGenreId";
    private static final String ARG_PAGE_TITLE = "argPageTitle";
    private PageType pageType;
    private int genreId;
    private String pageTitle;

    int currentPage;
    final int MAX_ITEMS = 100;
    public ListingFragment() {
        // Required empty public constructor
    }

    public static ListingFragment newInstance(PageType type, int genreId, String pageTitle) {
        ListingFragment fragment = new ListingFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LISTING_TYPE, type);
        args.putInt(ARG_GENRE_ID, genreId);
        args.putString(ARG_PAGE_TITLE,pageTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        FragmentActivity fragmentActivity = getActivity();
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        fragmentActivity.getOnBackPressedDispatcher();

        if (getArguments() != null) {
            pageType = (PageType) getArguments().getSerializable(ARG_LISTING_TYPE);
            genreId = getArguments().getInt(ARG_GENRE_ID);
            pageTitle = getArguments().getString(ARG_PAGE_TITLE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        return true;
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
        getActivity().setTitle(pageTitle);
        View view = inflater.inflate(R.layout.fragment_listing, container, false);

        recyclerView = view.findViewById(R.id.listingRecyclerView);

        currentPage = 1;
        shimmerFrameLayout = view.findViewById(R.id.listingShimmerLayout);
        shimmerFrameLayout.startShimmer();

        GameAdapter adapter = new GameAdapter(new ArrayList<>());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        RequestCallback callback = new RequestCallback() {
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


            @Override
            public void onSuccess(Game result) {}
        };
        RequestController requestController = new RequestController();
        switch (pageType) {
            case LATEST: {
                requestController.getLatest(currentPage, callback);
                break;
            }
            case POPULAR:{
                requestController.getPopular(currentPage, callback);
                break;
            }
            case GENRE:{
                requestController.getGenreData(genreId, callback);
                break;
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
                requestController.getLatest(currentPage,new RequestCallback() {
                    @Override
                    public void onSuccess(List<Game> result) {
                        adapter.addAll(result);
                    }

                    @Override
                    public void onSuccess(Game result) {
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