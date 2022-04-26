package com.example.gameloop.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.gameloop.R;
import com.example.gameloop.controllers.GameController;
import com.example.gameloop.controllers.SingleGameCallback;
import com.example.gameloop.models.Game;

public class GameFragment extends Fragment {
    private static final String ARG_ID = "argId";
    FragmentActivity fragmentActivity;

    private int id;
    private Game game;

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

        GameController controller = new GameController();
        controller.getGameData(id, new SingleGameCallback() {
            @Override
            public void onSuccess(Game result) {
                game = result;
                fragmentActivity.setTitle(game.getName());
                TextView text = view.findViewById(R.id.description);
                text.setText(game.getDescription());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


        return view;
    }
}