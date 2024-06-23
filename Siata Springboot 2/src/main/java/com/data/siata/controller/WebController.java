package com.data.siata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/dashboard")
    public String home() {
        return "forward:/dashboard.html";
    }

    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }

    @GetMapping("/signup")
    public String register() {
        return "forward:/signup.html";
    }

    @GetMapping("/kalender")
    public String calendar() {
        return "forward:/kalender.html";
    }

    @GetMapping("/listEvent")
    public String listEvent() {
        return "forward:/listEvent.html";
    }
}