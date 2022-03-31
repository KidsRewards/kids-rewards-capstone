package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homePage(){
        return "homepage";
    }

    @GetMapping("/about-us")
    public String showAboutUs() {
        return "about-us";
    }
}
