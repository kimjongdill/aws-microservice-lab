package com.example.restservice;

public class ExitProgram implements IDoTask{
    public void execute() throws Exception
    {
        Thread.sleep(5000);
        System.exit(0);
    }
}
