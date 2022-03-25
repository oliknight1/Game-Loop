package com.example.gameloop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.gameloop.models.Game;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Game> gameList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        generateTestData();
        setAdapter();
    }

    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(gameList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private void generateTestData() {
        gameList.add( new Game( "game 1"));
        gameList.add( new Game( "game 2"));
        gameList.add( new Game( "game 3"));
        gameList.add( new Game( "game 4"));
    }
}