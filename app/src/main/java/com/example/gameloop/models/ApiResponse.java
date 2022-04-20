package com.example.gameloop.models;

import com.google.gson.JsonElement;

public class ApiResponse {
    private int count;
    private String next;
    private String previous;
    private JsonElement results;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }


    public JsonElement getResults() {
        return results;
    }
}
