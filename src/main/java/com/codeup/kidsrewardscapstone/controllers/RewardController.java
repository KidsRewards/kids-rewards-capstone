package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Reward;
import com.codeup.kidsrewardscapstone.models.User;
import com.codeup.kidsrewardscapstone.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codeup.kidsrewardscapstone.repositories.RewardRepository;

@Controller
public class RewardController {
    private RewardRepository rewardsDao;
    private UserRepository usersDao;


    public RewardController(RewardRepository rewardsDao, UserRepository usersDao) {
        this.rewardsDao = rewardsDao;
        this.usersDao = usersDao;
    }

//    @GetMapping("/rewards")
//    public String viewRewards(Model model) {
//        model.addAttribute("allRewards", rewardsDao.findAll());
//        return "rewards/index";
//    }

    @GetMapping("/rewards")
    public String home(Model model) {
        model.addAttribute("allRewards", rewardsDao.findAll());
        return "rewards/index";
    }

    @GetMapping("/rewards/{id}")
    public String rewardDetails(@PathVariable long id, Model model) {
        model.addAttribute("singleReward", rewardsDao.getById(id));
        return "rewards/show";
    }

    @GetMapping("/rewards/create")
    public String showCreateForm(Model model) {
        model.addAttribute("newReward", new Reward());
        return "rewards/create";
    }

    @PostMapping("/rewards/create")
    public String createReward(@ModelAttribute Reward newReward){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newReward.setUser(loggedInUser);
        rewardsDao.save(newReward);
        return "redirect:/rewards/user-rewards-all";
    }

    @GetMapping("/rewards/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {
        Reward rewardtoEdit = rewardsDao.getById(id);
        model.addAttribute("rewardToEdit", rewardsDao.getById(id));
        return "rewards/edit";
    }

    @PostMapping("/rewards/{id}/edit")
    public String submitEdit(@ModelAttribute Reward rewardToEdit, @PathVariable long id) {
        rewardsDao.save(rewardToEdit);
        return "redirect:/rewards";
    }

//    Grants the ability for the user to delete a reward if implemented in the html
    @GetMapping("/rewards/{id}/delete")
    public String deleteReward(@PathVariable long id) {
        rewardsDao.delete(rewardsDao.getById(id));
        return "redirect:/rewards/user-rewards-all";
    }

//    Shows all rewards in the reward store, need to refactor to only show the rewards associated with single user
    @GetMapping("/rewards/user-rewards-all")
    public String viewRewards(Model model) {
        model.addAttribute("allRewards", rewardsDao.findAll());
        return "rewards/user-rewards-all";
    }

    @GetMapping("/rewards/index")
    public String showRewardStore(Model model){
        return "rewards/show";
    }
}// END class
