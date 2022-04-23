package com.example.gameloop.models;

import com.google.gson.annotations.SerializedName;

public class Game {
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("background_image")
    private String backgroundImage;

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Game(String name, String imgSrc) {
        this.name = name;
        this.backgroundImage = imgSrc;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
}
