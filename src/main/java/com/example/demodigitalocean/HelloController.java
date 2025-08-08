package com.example.demodigitalocean;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // GET /hello → "Hello, DigitalOcean!"
    @GetMapping("/hello")
    public String hello() {
        return "Hello, DigitalOcean!";
    }
}