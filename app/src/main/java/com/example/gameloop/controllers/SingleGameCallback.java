package com.example.gameloop.controllers;

import com.example.gameloop.models.Game;


public interface SingleGameCallback {
    void onSuccess(Game result);

    void onFailure(Throwable t);
}
