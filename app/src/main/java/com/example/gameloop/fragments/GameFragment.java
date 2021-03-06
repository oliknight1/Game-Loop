package com.example.gameloop.fragments;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gameloop.R;
import com.example.gameloop.controllers.RequestCallback;
import com.example.gameloop.controllers.RequestController;
import com.example.gameloop.models.Game;

import java.util.List;

public class GameFragment extends Fragment {
    private static final String ARG_ID = "argId";
    private static final String ARG_TITLE= "argTitle";
    FragmentActivity fragmentActivity;

    private int id;
    private String title;
    ImageView gameImg;
    TextView description;
    TextView releaseDate;
    TextView metacritic;
    TextView descriptionTitle;
    ScrollView scrollView;
    ProgressBar spinner;

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance(int id, String title) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putString(ARG_TITLE,title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        fragmentActivity = getActivity();
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        fragmentActivity.getOnBackPressedDispatcher();


        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
            title = getArguments().getString(ARG_TITLE);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        return true;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentActivity.setTitle(title);

        View view = inflater.inflate(R.layout.fragment_game, container, false);

        scrollView = view.findViewById(R.id.gameScrollView);
        scrollView.setAlpha(0f);

        gameImg = view.findViewById(R.id.cardImg);

        RequestController controller = new RequestController();
        controller.getGameData(id, new RequestCallback() {
            @Override
            public void onSuccess(List<Game> result) {}

            @Override
            public void onSuccess(Game result) {
                descriptionTitle = view.findViewById(R.id.descriptionTitle);
                descriptionTitle.setVisibility(View.VISIBLE);

                description = view.findViewById(R.id.description);
                if(result.getDescription() != null){
                    description.setText(result.getDescription());
                }

                Glide.with(view).load(result.getBackgroundImage()).into(gameImg);

                releaseDate = view.findViewById(R.id.release_date);
                if(result.getReleased() != null) {
                    releaseDate.setText("Release date: " + result.getReleased());
                }

                metacritic = view.findViewById(R.id.metacritic);
                if(result.getMetacritic() != null){
                    metacritic.setText(result.getMetacritic());
                    GradientDrawable metacriticBox = (GradientDrawable) metacritic.getBackground();
                    int metacriticInt = Integer.parseInt(result.getMetacritic());

                    // Change border colour based on the score
                    if (metacriticInt < 50 ) {
                        metacriticBox.setStroke(3,Color.RED);
                    } else if (metacriticInt >= 50 && metacriticInt <75) {
                        metacriticBox.setStroke(3,Color.rgb(255, 165, 0));
                    } else {
                        metacriticBox.setStroke(3,Color.GREEN);
                    }

                    metacritic.setVisibility(View.VISIBLE);
                }

                spinner = view.findViewById(R.id.progressBar);
                long spinnerDur = 100;
                spinner.animate().alpha(0f).setDuration(spinnerDur);
                scrollView.animate().alpha(1f).setStartDelay(spinnerDur).setDuration(1000);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        });


        return view;
    }
}