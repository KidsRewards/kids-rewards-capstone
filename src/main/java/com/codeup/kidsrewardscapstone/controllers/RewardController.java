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

    @GetMapping("/rewards")
    public String viewRewards(Model model) {
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
    public String create(
            @RequestParam(name = "icon") String icon,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body,
            @RequestParam(name = "points") Long points
    ) {
        Reward reward = new Reward();
        reward.setIcon(icon);
        reward.setTitle(title);
        reward.setBody(body);
        reward.setPoints(points);

        rewardsDao.save(reward);
        return "redirect:/rewards";
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

    @GetMapping("/rewards/{id}/delete")
    public String delete(@PathVariable long id) {
        rewardsDao.deleteById(id);
        return "redirect:/rewards";
    }
}// END class
