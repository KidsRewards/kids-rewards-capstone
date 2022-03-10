package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Reward;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.codeup.kidsrewardscapstone.repositories.RewardRepository;

@Controller
public class RewardController {
    private RewardRepository rewardDao;

    public RewardController(RewardRepository rewardDao) {
        this.rewardDao = rewardDao;
    }

//    @GetMapping("/rewards")
//    public String showRewards(Model model) {
//        model.addAttribute("reward", new Reward());
//        return "rewards/reward";
//    }

    @GetMapping("/rewards")
    public String showRewards(Model model) {
        model.addAttribute("allRewards", rewardDao.findAll());
        return "rewards/reward";
    }

    @GetMapping("/rewards/create")
    public String showCreateForm(Model model) {
        model.addAttribute("reward", new Reward());
        model.addAttribute("rewards", rewardDao.findAll());
        return "rewards/create";
    }


}// END class
