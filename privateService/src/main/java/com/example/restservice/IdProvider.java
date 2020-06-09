package com.example.restservice;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdProvider {

    private String myId;

    public IdProvider(){
        this.myId = UUID.randomUUID().toString();
    }

    public String getId(){
        return myId;
    }
}
