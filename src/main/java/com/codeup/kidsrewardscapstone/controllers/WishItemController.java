package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.repositories.WishItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WishItemController {
    private WishItemRepository wishDao;

    public WishItemController(WishItemRepository wishDao)   {
        this.wishDao = wishDao;
    }

    @GetMapping("/wishitems")
    public String showWishItems(@PathVariable long id, Model model) {
        model.addAttribute("wishitem", wishDao.getById(id));
        return "wishitem";
    }
}
