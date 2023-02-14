package com.example.demofragments.Models;

import com.example.demofragments.Adapters.ResponseAdapter;

public class Response {
    private String text;

    public Response(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

}
