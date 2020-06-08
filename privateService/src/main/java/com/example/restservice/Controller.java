package com.example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Controller {

    private final String template = "Hello %s";
    private final String awsMetadata = "http://http://169.254.169.254/latest/meta-data/local-ipv4";
    private String myIp;

    public Controller() {

        try{
            URL url = new URL(awsMetadata);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            myIp = con.getResponseMessage();
        }
        catch (Exception e){
            myIp = e.getMessage();
        }

    }

    @GetMapping("/greeting")
    public Response greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Response(myIp, String.format(template, name));
    }

    @GetMapping("/crash")
    public Response crash() {
        IDoTask task = new ExitProgram();
        WaitAndExecute w = new WaitAndExecute(task);
        Thread t = new Thread(w);
        t.start();
        return new Response(myIp, "Exiting in 5 seconds");
    }

    @GetMapping("/infinite")
    public Response infinite() {
        IDoTask task = new InfiniteCounter();
        WaitAndExecute w = new WaitAndExecute(task);
        Thread t = new Thread(w);
        t.start();
        return new Response(myIp, "Now doing useless work");
    }

    @GetMapping("/health")
    public Response health()
    {
        return new Response(myIp, "True");
    }
}
