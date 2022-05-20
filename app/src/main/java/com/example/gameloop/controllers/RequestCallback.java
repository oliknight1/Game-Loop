package com.example.gameloop.controllers;

import com.example.gameloop.models.Game;
import com.example.gameloop.models.Genre;

import java.util.List;

public interface RequestCallback {

    void onSuccess(List<Game> result);

    void onSuccess(Game result);

//    void onSuccess(List<Genre> result);

    void onFailure(Throwable t);

}
