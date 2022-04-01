package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.User;
import com.codeup.kidsrewardscapstone.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private UserRepository usersDao;

    public HomeController(UserRepository usersDao) {
        this.usersDao = usersDao;
    }

    @GetMapping("/")
    public String homePage(){
        return "homepage";
    }

    @GetMapping("/about-us")
    public String showAboutUs(Model model) {
        if(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));
            return "about-us";
        } else{
            return "about-us-two";
        }
    }
}