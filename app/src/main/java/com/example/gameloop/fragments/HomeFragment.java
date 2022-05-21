package com.example.gameloop.fragments;

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
        setHasOptionsMenu(true);
        Log.i("BACKSTACK",Integer.toString(getActivity().getSupportFragmentManager().getBackStackEntryCount()));
        if(getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0){
            FragmentActivity fragmentActivity = getActivity();
            ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            fragmentActivity.getOnBackPressedDispatcher();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        return true;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentActivity fragmentActivity = getActivity();
        fragmentActivity.setTitle("Home");
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