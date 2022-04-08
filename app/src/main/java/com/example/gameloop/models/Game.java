package com.example.gameloop.models;

public class Game {
    private String id;
    private String name;

    public Game(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
