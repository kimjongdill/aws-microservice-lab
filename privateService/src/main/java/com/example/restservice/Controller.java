package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Controller {

    @Autowired
    private IdProvider idProvider;

    private final String template = "Hello %s";

    @GetMapping("/greeting")
    public Response greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Response(idProvider.getId(), String.format(template, name));
    }

    @GetMapping("/crash")
    public Response crash() {
        IDoTask task = new ExitProgram();
        WaitAndExecute w = new WaitAndExecute(task);
        Thread t = new Thread(w);
        t.start();
        return new Response(idProvider.getId(), "Exiting in 5 seconds");
    }

    @GetMapping("/infinite")
    public Response infinite() {
        IDoTask task = new InfiniteCounter();
        WaitAndExecute w = new WaitAndExecute(task);
        Thread t = new Thread(w);
        t.start();
        return new Response(idProvider.getId(), "Now doing useless work");
    }

    @GetMapping("/health")
    public Response health()
    {
        return new Response(idProvider.getId(), "True");
    }
}
