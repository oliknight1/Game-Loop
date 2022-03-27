package com.example.gameloop.models;

public class Game {
    private String id;
    private String name;

    public Game(String title) {
        this.name = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
