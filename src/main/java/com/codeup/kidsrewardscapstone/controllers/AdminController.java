package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Admin;
import com.codeup.kidsrewardscapstone.repositories.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    private AdminRepository adminDao;
    private PasswordEncoder passwordEncoder;

    public AdminController(AdminRepository adminDao, PasswordEncoder passwordEncoder){
        this.adminDao = adminDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("admin", new Admin());
        return "admins/register";
    }

    @PostMapping("/register")
    public String saveAdmin(@ModelAttribute Admin admin){
        String hash = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(hash);
        adminDao.save(admin);
        return "redirect:/login";
    }


}
