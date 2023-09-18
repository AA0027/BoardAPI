package com.example.test.controller;

import jakarta.persistence.GeneratedValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    String home()
    {
        return "home";
    }
}
