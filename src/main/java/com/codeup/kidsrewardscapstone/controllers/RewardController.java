package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Family;
import com.codeup.kidsrewardscapstone.models.Reward;
import com.codeup.kidsrewardscapstone.models.User;
import com.codeup.kidsrewardscapstone.repositories.FamilyRepository;
import com.codeup.kidsrewardscapstone.repositories.UserRepository;
import com.codeup.kidsrewardscapstone.services.EmailService;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codeup.kidsrewardscapstone.repositories.RewardRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RewardController {
    private RewardRepository rewardsDao;
    private UserRepository usersDao;
    private FamilyRepository familiesDao;
    private EmailService emailService;


    public RewardController(RewardRepository rewardsDao, UserRepository usersDao, FamilyRepository familiesDao, EmailService emailService) {
        this.rewardsDao = rewardsDao;
        this.usersDao = usersDao;
        this.familiesDao = familiesDao;
        this.emailService = emailService;
    }

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

//    Shows reward form and allows the ability to select from a list of children to assign the reward to
    @GetMapping("/rewards/create")
    public String showCreateForm(Model model) {
        model.addAttribute("newReward", new Reward());

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Family currentFamily = familiesDao.findFamilyByUsers(loggedInUser);
        List<User> wholeFamily = usersDao.findUsersByFamilies(currentFamily);
        List<User> children = new ArrayList<>();

        for(User user : wholeFamily){
            if(!user.getParent()){
                System.out.println(user.getId());
                children.add(user);
            }
        }

        model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));
        model.addAttribute("children", children);
        return "rewards/create";
    }

    @PostMapping("/rewards/create")
    public String createReward(@ModelAttribute Reward newReward, @RequestParam(name="childId") long childId){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newReward.setUser(usersDao.getById(childId));
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


//---Working On displaying Rewards to particular User
//    Shows all rewards in the reward store, need to refactor to only show the rewards associated with single user
    @GetMapping("/rewards/user-rewards-all")
    public String viewRewards(Model model) {
        //Gets user id
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Gets users family by using familyDao and inputting the userId of current logged in user
//        Family family = familiesDao.findFamilyByUsers(loggedInUser);
//        System.out.println("Family ID: " + family.getId());
//        family.getId();
//        model.addAttribute("allRewards", rewardsDao.getById(loggedInUser.getId()));

        model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));
        model.addAttribute("allRewards", rewardsDao.findRewardByUser(loggedInUser));
//        model.addAttribute("allRewards", rewardsDao.findAll());
        model.addAttribute("user", usersDao.getById(loggedInUser.getId()));
        return "rewards/user-rewards-all";
    }

    //        Family family = familiesDao.findFamilyByUsers(loggedInUser);
//        List<Reward> rewardsForFamily = rewardsDao.findRewardByUser(usersDao.getById());
//        loggedInUser.getId(); //returns id of  5
//---Working on displaying Rewards to Particular User


    @GetMapping("/rewards/index")
    public String showRewardStore(Model model){
        return "rewards/show";
    }

//    @GetMapping("/rewards/{id}/purchase")
    @PostMapping("/rewards/{id}/purchase")
    public String purchaseReward(@ModelAttribute Reward purchasedReward, @PathVariable long id){
        Reward rewardToPurchase = rewardsDao.getById(id);

        User assignedUser = rewardToPurchase.getUser();

        if(assignedUser.getPointsTotal() >= rewardToPurchase.getPoints()) {
            Long newTotal = assignedUser.getPointsTotal() - rewardToPurchase.getPoints();
            assignedUser.setPointsTotal(newTotal);
            usersDao.save(assignedUser);


            Family currentFamily = familiesDao.findFamilyByUsers(assignedUser);

            List<User> familyMembers = currentFamily.getUsers();
            List<User> parents = new ArrayList<>();

            for (User member : familyMembers) {
                if (member.getParent()) {
                    parents.add(member);
                }
            }

            for (User parent : parents) {
                System.out.println(parent.getEmail());
                try {
                    emailService.sendTextEmail(parent.getEmail(), rewardToPurchase);
                } catch (IOException ex) {
                    throw new RuntimeException("Sorry your email cannot be sent");
                }
            }
        }

        return "redirect:/rewards/user-rewards-all";
    }
}// END class
