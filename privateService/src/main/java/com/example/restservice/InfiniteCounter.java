package com.example.restservice;

public class InfiniteCounter implements IDoTask{
    public void execute () throws Exception
    {
        int counter = 0;
        while(true){
            counter++;
        }
    }
}
