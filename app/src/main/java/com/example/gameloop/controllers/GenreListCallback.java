package com.example.gameloop.controllers;


import com.example.gameloop.models.Genre;

import java.util.List;

public interface GenreListCallback {
    void onSuccess(List<Genre> result);

    void onFailure(Throwable t);
}
