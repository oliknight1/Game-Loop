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
import com.example.gameloop.models.PageType;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String prevBackStackTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment(),"home");
        binding.nav.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment(),"home");
                    break;
                case R.id.popular:
                    // Pass 0 as no genres have the id 0
                    replaceFragment(ListingFragment.newInstance(PageType.POPULAR,0,"Popular"),"popular");
                    break;
                case R.id.latest:
                    replaceFragment(ListingFragment.newInstance(PageType.LATEST,0,"Latest"),"latest");
                    break;
                case R.id.all_genres:
                    replaceFragment(new AllGenresFragment(),"genres");
                    break;
            }

            return true;
        });

    }
    private void replaceFragment(Fragment fragment, String backStackTag) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        if(prevBackStackTag != null){
            fragmentTransaction.addToBackStack(prevBackStackTag);
        }
        fragmentTransaction.commit();

        prevBackStackTag = backStackTag;

    }
}