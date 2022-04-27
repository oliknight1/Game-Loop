package com.example.gameloop.models;

import android.text.Html;
import android.text.Spanned;

import com.google.gson.annotations.SerializedName;

public class Game {
    private int id;
    private String name;
    @SerializedName("background_image")
    private String backgroundImage;
    private String description;
    private String metacritic;
    private String released;

    public Game(int id, String name, String imgSrc, String description) {
        this.id = id;
        this.name = name;
        this.backgroundImage = imgSrc;
        this.description = description;
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

    public Spanned getDescription() {
        return Html.fromHtml(description,Html.FROM_HTML_MODE_COMPACT);
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getMetacritic() {
        return metacritic;
    }

    public String getReleased() {
        return released;
    }



}
