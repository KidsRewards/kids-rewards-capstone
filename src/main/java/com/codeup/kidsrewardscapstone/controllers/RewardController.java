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
    public String submitCreateForm(@ModelAttribute Reward newReward) {
        newReward.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        rewardsDao.save(newReward);
        return "redirect:/rewards";
    }

    @GetMapping("/rewards/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {
        Reward rewardtoEdit = rewardsDao.getById(id);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (rewardtoEdit.getUser().getId() == loggedInUser.getId()) {
            model.addAttribute("rewardToEdit", rewardtoEdit);
            return "rewards/edit";
        } else {
            return "redirect:/rewards";
        }
    }

    @PostMapping("/rewards/{id}/edit")
    public String submitEdit(@ModelAttribute Reward rewardToEdit, @PathVariable long id) {
        if (rewardsDao.getById(id).getUser().getId() == ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            rewardToEdit.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            ////////////*Example from posts (Regulus springblog code) but it isn't working: *////////////////////////
                 // grab the post from our DAO
                //    Post postToEdit = postsDao.getById(id);
            // use setters to set new values to the object
                //    postToEdit.setTitle(title);
                //    postToEdit.setBody(body);
            // save the object with new values
            ///////////////////////////////////////////////////////////////////////////////////////////////////////
            rewardsDao.save(rewardToEdit);
        }
        return "redirect:/rewards";
    }

    @GetMapping("/rewards/{id}/delete")
    public String delete(@PathVariable long id) {
        rewardsDao.deleteById(id);
        return "redirect:/rewards";
    }

}// END class
