package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.WishItem;
import com.codeup.kidsrewardscapstone.repositories.WishItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WishItemController {
    private WishItemRepository wishitemsDao;

    public WishItemController(WishItemRepository wishitemsDao) {
        this.wishitemsDao = wishitemsDao;
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

    @PostMapping("/wishitems/create")
    public String create(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body
    ) {
        WishItem wishItem = new WishItem();
        wishItem.setTitle(title);
        wishItem.setBody(body);

        wishitemsDao.save(wishItem);
        return "redirect:/wishitems";
    }

    @GetMapping("/wishitems/{id}/edit")
    public String showEditWishItems(@PathVariable long id, Model model) {
        model.addAttribute("wishItemToEdit", wishitemsDao.getById(id));
        return "wishitems/edit";
    }

    @PostMapping("/wishitems/{id}/edit")
    public String submitWishItemToEdit(@ModelAttribute WishItem wishItemToEdit, @PathVariable long id) {

        wishitemsDao.save(wishItemToEdit);
        return "redirect:/wishitems";
    }

    @GetMapping("/wishitems/{id}/delete")
    public String delete(@PathVariable long id) {
        wishitemsDao.deleteById(id);
        return "redirect:/wishitems";
    }
}

