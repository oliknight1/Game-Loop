package com.example.gameloop.models;

import android.text.Html;
import android.text.Spanned;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Genre {
    private int id;
    private String name;
    @SerializedName("image_background")
    private String backgroundImage;
    private List<Game> games;

    public Genre(int id, String name, String imgSrc, String description) {
        this.id = id;
        this.name = name;
        this.backgroundImage = imgSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public List<Game> getGames () {
        return games;
    }

}
