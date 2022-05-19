package com.example.gameloop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.gameloop.databinding.ActivityMainBinding;
import com.example.gameloop.fragments.AllGenresFragment;
import com.example.gameloop.fragments.HomeFragment;
import com.example.gameloop.fragments.ListingFragment;
import com.example.gameloop.fragments.PopularFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment( new HomeFragment());
        binding.nav.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){
                case R.id.home:
                    replaceFragment( new HomeFragment());
                    break;
                case R.id.popular:
                    replaceFragment( new PopularFragment());
                    break;
                case R.id.latest:
                    ListingFragment fragment = ListingFragment.newInstance("LATEST");
                    AppCompatActivity activity = MainActivity.this;
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
                    break;
                case R.id.all_genres:
                    replaceFragment( new AllGenresFragment());
                    break;
            }

            return true;
        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
//        GameFragment fragment = GameFragment.newInstance(game.getId());
//        AppCompatActivity activity = (AppCompatActivity) view.getContext();
//        activity.getSupportFragmentManager().beginTransaction().addToBackStack("latest").replace(R.id.frame_layout,fragment).commit();
    }
}