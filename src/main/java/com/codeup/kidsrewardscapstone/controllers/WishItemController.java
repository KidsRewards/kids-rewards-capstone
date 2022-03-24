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
//        User currentUser = (User) usersDao.findById(loggedInUser.getId());
//        List<WishItem> currentUserWishItems = wishitemsDao.findByUser(loggedInUser);
        Family currentFamily = familyDao.findFamilyByUsers(loggedInUser);
        List<WishItem> allWishItems = wishitemsDao.findByUser_Families(currentFamily);
//        for (User user: currentFamily.getUsers()) {
//            System.out.println(user.getId());
//        }
        if (!loggedInUser.getParent()) {
            List<WishItem> singleKidWishItem = wishitemsDao.findByUser(loggedInUser);
            model.addAttribute("allWishitems", singleKidWishItem);
        } else {
            model.addAttribute("allWishitems", allWishItems);
        }
        model.addAttribute("user", loggedInUser.getParent());
//        model.addAttribute("allWishitems", allWishItems);
        return "wishitems/index";
    }

    @GetMapping("/wishitems/{id}")
    public String showWishItems(@PathVariable long id, Model model) {
        model.addAttribute("singleWishitem", wishitemsDao.getById(id));
        return "wishitems/show";
    }

    @GetMapping("/wishitems/create")
    public String showCreateForm(Model model) {
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
    public String delete(@PathVariable long id) {
        wishitemsDao.deleteById(id);
        return "redirect:/wishitems";
    }

    @GetMapping("/wishitems/approved")
    public String showWishItemApproved(@PathVariable long id, Model model) {
        model.addAttribute("showWishItemApproved", wishitemsDao.findByStatus(2L));
        return "wishItems/approved";
    }

    @PostMapping("/wishitems/{id}/approved")
    public String wishItemApproved(@ModelAttribute WishItem wishItemApproved, @PathVariable long id, @RequestParam(name = "points")String points) {
        Reward newReward = new Reward();
        newReward.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        newReward.setTitle(wishItemApproved.getTitle());
        newReward.setBody(wishItemApproved.getBody());
        long newPoints = Long.parseLong(points);
        newReward.setPoints(newPoints);
        newReward.setIcon("placeholder");
        wishitemsDao.delete(wishItemApproved);
        rewardsDao.save(newReward);
        return "redirect:/rewards/user-rewards-all";
    }

    @GetMapping("/wishitems/{id}/approved")
    public String showWishItemAddForm(@PathVariable long id, Model model) {
//        model.addAttribute("showWishItemApproved", wishitemsDao.findByStatus(2L));
        WishItem currentWishItem = wishitemsDao.getById(id);
                model.addAttribute("currentWishItem", currentWishItem);
        return "wishItems/approvedform";
    }
}


