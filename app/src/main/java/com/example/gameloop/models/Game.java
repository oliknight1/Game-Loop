package com.example.gameloop.models;

public class Game {
    private String id;
    private String name;
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
}
