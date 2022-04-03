package com.example.gameloop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.gameloop.databinding.ActivityMainBinding;
import com.example.gameloop.fragments.AllGenresFragment;
import com.example.gameloop.fragments.HomeFragment;
import com.example.gameloop.fragments.LatestFragment;
import com.example.gameloop.fragments.PopularFragment;
import com.example.gameloop.models.ApiResponse;
import com.example.gameloop.models.Game;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

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
                    replaceFragment( new LatestFragment());
                    break;
                case R.id.all_genres:
                    replaceFragment( new AllGenresFragment());
                    break;

            }

            return true;
        });
        String baseUrl = "https://api.rawg.io/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RAWGApi rawgApi = retrofit.create(RAWGApi.class);

        recyclerView = findViewById(R.id.recyclerView);
        Call<ApiResponse> call = rawgApi.getAllGames();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if( !response.isSuccessful() ) {
                    // Handle Error
                    return;
                }
//                setAdapter(games);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("FAIL", "Failed: " + t.getMessage());
            }
        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    private void setAdapter( List<Game> gameList) {
        RecyclerAdapter adapter = new RecyclerAdapter(gameList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

}