package com.example.gameloop.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gameloop.MainActivity;
import com.example.gameloop.R;
import com.example.gameloop.models.PageType;

public class HomeFragment extends Fragment {
    Button latestBtn;
    Button popularBtn;
    Button allGenresBtn;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        latestBtn = view.findViewById(R.id.latestBtn);

        latestBtn.setOnClickListener(view1 -> {
            ListingFragment fragment = ListingFragment.newInstance(PageType.LATEST,0,"Latest");
            replaceFragment(fragment, view1);
        });

        popularBtn = view.findViewById(R.id.popularBtn);
        popularBtn.setOnClickListener(view1 -> {
            ListingFragment fragment = ListingFragment.newInstance(PageType.POPULAR,0,"Latest");
            replaceFragment(fragment, view1);
        });

        allGenresBtn = view.findViewById(R.id.allGenresBtn);
        allGenresBtn.setOnClickListener(view1 -> {
            replaceFragment(new AllGenresFragment(),view1);
        });
        return view;
    }
    private void replaceFragment(Fragment fragment, View view){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();

    }
}