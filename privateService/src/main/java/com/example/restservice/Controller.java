package com.example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Controller {

    private AtomicLong counter = new AtomicLong(0);
    private String template = "Hello %s";

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/crash")
    public String crash() {
        IDoTask task = new ExitProgram();
        WaitAndExecute w = new WaitAndExecute(task);
        Thread t = new Thread(w);
        t.start();
        return "Exiting in 5 seconds";
    }

    @GetMapping("/infinite")
    public String infinite() {
        IDoTask task = new InfiniteCounter();
        WaitAndExecute w = new WaitAndExecute(task);
        Thread t = new Thread(w);
        t.start();
        return "Now doing useless work";
    }

    @GetMapping("/health")
    public boolean health()
    {
        return true;
    }
}
