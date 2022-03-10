package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Reward;
import com.codeup.kidsrewardscapstone.models.User;
import com.codeup.kidsrewardscapstone.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.codeup.kidsrewardscapstone.repositories.RewardRepository;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RewardController {
    private RewardRepository rewardsDao;
    private UserRepository userDao;


    public RewardController(RewardRepository rewardDao) {
        this.rewardsDao = rewardDao;
    }

    @GetMapping("/rewards")
    public String showRewards(Model model) {
        model.addAttribute("allRewards", rewardsDao.findAll());
        return "rewards/reward";
    }

    @GetMapping("/rewards/create")
    public String showCreateForm(Model model) {
        model.addAttribute("newReward", new Reward());
        return "rewards/create";
    }

    @PostMapping("/rewards/create")
    public String submitCreateForm(@ModelAttribute Reward newReward) {
        newReward.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        rewardsDao.save(newReward);
        return "redirect:/rewards";
    }
}// END class
