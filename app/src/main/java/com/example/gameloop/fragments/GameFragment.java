package com.example.gameloop.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gameloop.R;
import com.example.gameloop.controllers.GameController;
import com.example.gameloop.controllers.SingleGameCallback;
import com.example.gameloop.models.Game;

public class GameFragment extends Fragment {
    private static final String ARG_ID = "argId";
    FragmentActivity fragmentActivity;

    private int id;
    ImageView gameImg;
    TextView description;
    TextView releaseDate;
    TextView metacritic;
    TextView descriptionTitle;

    public GameFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(int id) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        fragmentActivity = getActivity();
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Remove title first
        fragmentActivity.setTitle("");

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        fragmentActivity.getOnBackPressedDispatcher();


        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
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

        View view = inflater.inflate(R.layout.fragment_game, container, false);

        gameImg = view.findViewById(R.id.gameImg);

        GameController controller = new GameController();
        controller.getGameData(id, new SingleGameCallback() {
            @Override
            public void onSuccess(Game result) {
                fragmentActivity.setTitle(result.getName());

                descriptionTitle = view.findViewById(R.id.descriptionTitle);
                descriptionTitle.setVisibility(View.VISIBLE);

                description = view.findViewById(R.id.description);
                description.setText(result.getDescription());

                Glide.with(view).load(result.getBackgroundImage()).into(gameImg);

                releaseDate = view.findViewById(R.id.release_date);
                releaseDate.setText("Release date: " + result.getReleased());

                metacritic = view.findViewById(R.id.metacritic);
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

            @Override
            public void onFailure(Throwable t) {

            }
        });


        return view;
    }
}