package com.codeup.kidsrewardscapstone.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import repositories.RewardRepository;

@Controller
public class RewardController {
    private RewardRepository rewardDao;

    public RewardController(RewardRepository rewardDao) {
        this.rewardDao = rewardDao;
    }

    @GetMapping("/rewards")
    public String showRewards(@PathVariable long id, Model model) {
        model.addAttribute("reward", rewardDao.getById(id));
        return "reward";
    }
}
