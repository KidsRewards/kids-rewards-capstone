package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.*;
import com.codeup.kidsrewardscapstone.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WishItemController {
    private WishItemRepository wishitemsDao;
    private UserRepository usersDao;
    private StatusRepository statusDao;
    private RewardRepository rewardsDao;
    private FamilyRepository familyDao;


    public WishItemController(WishItemRepository wishitemsDao, UserRepository usersDao, StatusRepository statusDao, RewardRepository rewardsDao, FamilyRepository familyDao) {
        this.wishitemsDao = wishitemsDao;
        this.usersDao = usersDao;
        this.statusDao = statusDao;
        this.rewardsDao = rewardsDao;
        this.familyDao = familyDao;
    }

    @GetMapping("/wishitems")
    public String showWishItems(Model model) {
//        Logged in user can only see there WishItems list.
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Family currentFamily = familyDao.findFamilyByUsers(loggedInUser);
        List<WishItem> allWishItems = wishitemsDao.findByUser_Families(currentFamily);

        if (!loggedInUser.getParent()) {
            List<WishItem> singleKidWishItem = wishitemsDao.findByUser(loggedInUser);
            model.addAttribute("allWishitems", singleKidWishItem);
        } else {
            model.addAttribute("allWishitems", allWishItems);
        }
        model.addAttribute("user", loggedInUser.getParent());
        model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));
        return "wishitems/index";
    }

    @GetMapping("/wishitems/{id}")
    public String showWishItems(@PathVariable long id, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));
        model.addAttribute("singleWishitem", wishitemsDao.getById(id));
        return "wishitems/show";
    }

    @GetMapping("/wishitems/create")
    public String showCreateForm(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));
        model.addAttribute("newWishitem", new WishItem());
        return "wishitems/create";
    }

    @PostMapping("/wishitems/create")
    public String createWishItem(@ModelAttribute WishItem newWishItem){
        newWishItem.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        newWishItem.setStatus(statusDao.getById(1L));
        wishitemsDao.save(newWishItem);
        return "redirect:/wishitems";
    }

    @GetMapping("/wishitems/{id}/edit")
    public String showEditWishItems(@PathVariable long id, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));
        model.addAttribute("wishItemToEdit", wishitemsDao.getById(id));
        return "wishitems/edit";
    }

    @PostMapping("/wishitems/{id}/edit")
    public String submitWishItemToEdit(@ModelAttribute WishItem wishItemToEdit, @PathVariable long id) {
        wishItemToEdit.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        wishItemToEdit.setStatus(statusDao.getById(1L));
        wishitemsDao.save(wishItemToEdit);
        return "redirect:/wishitems";
    }

    @GetMapping("/wishitems/{id}/delete")
    public String delete(@PathVariable long id, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));
        wishitemsDao.deleteById(id);
        return "redirect:/wishitems";
    }

    @GetMapping("/wishitems/approved")
    public String showWishItemApproved(@PathVariable long id, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));
        model.addAttribute("showWishItemApproved", wishitemsDao.findByStatus(2L));
        return "wishitems/approved";
    }

    @PostMapping("/wishitems/{id}/approved")
    public String wishItemApproved(@ModelAttribute WishItem wishItemApproved, @PathVariable long id, @RequestParam(name = "points")String points) {
        System.out.println(wishitemsDao.getById(id).getUser().getId());
        Reward newReward = new Reward();
        newReward.setUser(wishitemsDao.getById(id).getUser());
        newReward.setTitle(wishItemApproved.getTitle());
        newReward.setBody(wishItemApproved.getBody());
        long newPoints = Long.parseLong(points);
        newReward.setPoints(newPoints);
        newReward.setIcon("placeholder");
        wishitemsDao.delete(wishItemApproved);
        rewardsDao.save(newReward);
        return "redirect:/tasks/reviewform";
    }

    @GetMapping("/wishitems/{id}/approved")
    public String showWishItemAddForm(@PathVariable long id, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", usersDao.getById(loggedInUser.getId()));
        WishItem currentWishItem = wishitemsDao.getById(id);
        model.addAttribute("currentWishItem", currentWishItem);
        return "wishitems/approvedform";
    }
}


