package com.example.restservice;

public class WaitAndExecute implements Runnable {

    IDoTask _task;

    public WaitAndExecute(IDoTask task)
    {
        _task = task;
    }

    public void run(){
        try
        {
            _task.execute();
        }
        finally
        {
            return;
        }
    }
}
