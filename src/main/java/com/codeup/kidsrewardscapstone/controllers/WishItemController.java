package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Reward;
import com.codeup.kidsrewardscapstone.models.User;
import com.codeup.kidsrewardscapstone.models.WishItem;
import com.codeup.kidsrewardscapstone.repositories.UserRepository;
import com.codeup.kidsrewardscapstone.repositories.WishItemRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WishItemController {
    private WishItemRepository wishitemsDao;
    private UserRepository usersDao;


    public WishItemController(WishItemRepository wishitemsDao, UserRepository usersDao) {
        this.wishitemsDao = wishitemsDao;
        this.usersDao = usersDao;
    }

    @GetMapping("/wishitems")
    public String showWishItems(Model model) {
        model.addAttribute("allWishitems", wishitemsDao.findAll());
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

//    @PostMapping("/wishitems/create")
//    public String create(
//            @RequestParam(name = "title") String title,
//            @RequestParam(name = "body") String body
//    ) {
//        WishItem wishItem = new WishItem();
//        wishItem.setTitle(title);
//        wishItem.setBody(body);
//
//        wishitemsDao.save(wishItem);
//        return "redirect:/wishitems";
//    }

    @PostMapping("/wishitems/create")
    public String createWishItem(@ModelAttribute WishItem newWishItem){
        newWishItem.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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
        wishItemToEdit.setUser(usersDao.getById(1L));
        wishitemsDao.save(wishItemToEdit);
        return "redirect:/wishitems";
    }

    @GetMapping("/wishitems/{id}/delete")
    public String delete(@PathVariable long id) {
        wishitemsDao.deleteById(id);
        return "redirect:/wishitems";
    }

//    @GetMapping("/wishitems/user-wishitems-all")
//    public String viewWishItems(Model model) {
//        model.addAttribute("allWishitems", wishitemsDao.findAll());
//        return "wishitems/index";
//    }
}


