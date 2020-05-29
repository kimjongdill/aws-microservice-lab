package com.publicService.publicService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class PublicServiceController {

    String urlBase = "http://backend.local:8080/";

    private String makeCallToUrlString(String s) {
        String response;

        try
        {
            URL url = new URL(urlBase + s);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int status = conn.getResponseCode();
            response = s + " signal received: " + status;
        }
        catch (Exception e)
        {
            response = s + " signal failed";
        }

        return response;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        String response;
        try
        {
            URL url = new URL(urlBase + "greeting?name=" + name);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int status = conn.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            response = content.toString();
        }
        catch (Exception e)
        {
            response = "Call Failed";
        }

        return response;
    }

    @GetMapping("/crash")
    String Crash()
    {
        return makeCallToUrlString("/crash");
    }

    @GetMapping("/infinite")
    public String infinite() {
        return makeCallToUrlString("/infinite");
    }

    @GetMapping("/health")
    public Boolean health()
    {
        return true;
    }
}
