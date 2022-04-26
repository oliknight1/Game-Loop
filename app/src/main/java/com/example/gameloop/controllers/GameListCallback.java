package com.example.gameloop.controllers;

import com.example.gameloop.models.Game;

import java.util.List;

public interface GameListCallback {

    void onSuccess(List<Game> result);

    void onFailure(Throwable t);
}
