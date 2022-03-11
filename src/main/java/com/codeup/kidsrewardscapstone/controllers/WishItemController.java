package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.WishItem;
import com.codeup.kidsrewardscapstone.repositories.WishItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WishItemController {
    private WishItemRepository wishitemsDao;

    public WishItemController(WishItemRepository wishitemsDao) {
        this.wishitemsDao = wishitemsDao;
    }

    @GetMapping("/wishitems")
    public String showWishItems(Model model) {
        model.addAttribute("allWishitems", wishitemsDao.findAll());
        return "wishitems/wishitem";
    }

    @GetMapping("/wishitems/{id}")
    public String showWishItems(@PathVariable long id, Model model) {
        model.addAttribute("Wishitems", wishitemsDao.getById(id));
        return "wishitems/wishitem" + id;
    }
}