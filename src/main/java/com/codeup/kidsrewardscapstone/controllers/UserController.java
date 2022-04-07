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
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));
//            if(loggedInUser.getFamilies() != null && !loggedInUser.getParent()){
            if(familiesDao.findFamilyByUsers(loggedInUser) != null && !loggedInUser.getParent()){
                return "redirect:/tasks/index";
//            } else if(loggedInUser.getFamilies() != null && loggedInUser.getParent()){
            } else if(familiesDao.findFamilyByUsers(loggedInUser) != null && loggedInUser.getParent()){
                System.out.println(familiesDao.findFamilyByUsers(loggedInUser));
                return "redirect:/tasks/reviewform";
            } else{
            return "users/index";
        }
    }


    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/sign-up";
    }

    //Refactors Parent sign-up and sets isParent to true
    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user, Model model){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setParent(true);
        usersDao.save(user);
            return "redirect:/login";
    }

    @GetMapping("/update-password")
    public String showUserEditForm(Model model){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));

        model.addAttribute("user", loggedInUser);
        return "users/edit";
    }

    @PostMapping("/update-password")
    public String savePassword(@ModelAttribute User user, Model model){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));
        if(loggedInUser.getParent()){
            return "redirect:/tasks/reviewform";
        } else{
            return "redirect:/tasks/index";
        }
    }
}

