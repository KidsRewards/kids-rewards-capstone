package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Reward;
import com.codeup.kidsrewardscapstone.models.Task;
import com.codeup.kidsrewardscapstone.models.User;
import com.codeup.kidsrewardscapstone.models.WishItem;
import com.codeup.kidsrewardscapstone.repositories.StatusRepository;
import com.codeup.kidsrewardscapstone.repositories.UserRepository;
import com.codeup.kidsrewardscapstone.repositories.WishItemRepository;
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


    public WishItemController(WishItemRepository wishitemsDao, UserRepository usersDao, StatusRepository statusDao) {
        this.wishitemsDao = wishitemsDao;
        this.usersDao = usersDao;
        this.statusDao = statusDao;
    }

    @GetMapping("/wishitems")
    public String showWishItems(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<WishItem> currentUserWishItems = wishitemsDao.findByUser(loggedInUser);
        model.addAttribute("allWishitems", currentUserWishItems);
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
}


