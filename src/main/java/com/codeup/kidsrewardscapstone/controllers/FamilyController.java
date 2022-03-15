package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Family;
import com.codeup.kidsrewardscapstone.repositories.FamilyRepository;
import com.codeup.kidsrewardscapstone.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FamilyController {
    private FamilyRepository familiesDao;

    public FamilyController(FamilyRepository familiesDao){
        this.familiesDao = familiesDao;
    }

    @GetMapping("/createfamily")
    public String showFamilyForm(Model model){
        model.addAttribute("family", new Family());
        return "users/createfamily";
    }

    @PostMapping("/createfamily")
    public String saveFamilyName(@ModelAttribute Family family){
        familiesDao.save(family);
        return "redirect:/index";
    }

}