package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Family;
import com.codeup.kidsrewardscapstone.models.User;
import com.codeup.kidsrewardscapstone.repositories.FamilyRepository;
import com.codeup.kidsrewardscapstone.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserRepository usersDao;
    private PasswordEncoder passwordEncoder;
    private FamilyRepository familiesDao;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, FamilyRepository familiesDao) {
        this.usersDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.familiesDao = familiesDao;
    }

    @GetMapping("/index")
    public String showUsers(Model model) {
//        model.addAttribute("allUsers", usersDao.findAll());
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(loggedInUser.getFamilies() == null){
//            return "redirect:/createfamily";
//        } else {
            model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));
            model.addAttribute("familyName", familiesDao.findFamilyByUsers(loggedInUser));
            return "users/index";
//        }
    }

//    if(loggedInUser.getFamilies() == null){
//        return "redirect:/createfamily";
//    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/sign-up";
    }

    //Refactors Parent sign-up and sets isParent to true
    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setParent(true);
        usersDao.save(user);
        if(user.getFamilies() == null){
        return "redirect:/createfamily";
        } else {
            return "redirect:/login";
        }
    }

}

