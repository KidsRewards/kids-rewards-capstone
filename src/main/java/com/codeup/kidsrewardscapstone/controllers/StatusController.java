package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.repositories.StatusRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StatusController {
    private StatusRepository statusDao;

    public StatusController(StatusRepository statusDao) {
        this.statusDao = statusDao;
    }
    @GetMapping("/statuses")
    public String showStatus(@PathVariable long id, Model model){
        model.addAttribute("status", statusDao.getById(id));
        return "status";
    }
}