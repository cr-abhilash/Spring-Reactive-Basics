package com.example.springwebfluxdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {

    @GetMapping("/")
    public String WelcomeMessage(){
        return "Welcome to spring webflux project";
    }
}
