package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Reward;
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
        model.addAttribute("Wishitems", wishitemsDao.getById(id));
        return "wishitems/show";
    }

    @GetMapping("/wishitems/create")
    public String showCreate(Model model) {
        model.addAttribute("newWishitem", new WishItem());
        return "wishitems/create";
    }

    @PostMapping("/wishitems/create")
    public String create(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body
    ) {
        WishItem WishItem = new WishItem();
        WishItem.setTitle(title);
        WishItem.setBody(body);

        wishitemsDao.save(WishItem);
        return "redirect:/wishitems";
    }

    @GetMapping("/wishitems/{id}/edit")
    public String showEditWishItems(@PathVariable long id, Model model) {
        model.addAttribute("rewardToEdit", wishitemsDao.getById(id));
        return "wishitems/edit";
    }

    @PostMapping("/wishitems/{id}/edit")
    public String submitWishItemToEdit(@ModelAttribute WishItem wishItemToEdit, @PathVariable long id) {
//        rewardToEdit.setUser(usersDao.getById(1L));
        wishitemsDao.save(wishItemToEdit);
        return "redirect:/wishitems/" + id;
    }

    @GetMapping("/wishitems/{id}/delete")
    public String delete(@PathVariable long id) {
        wishitemsDao.deleteById(id);
        return "redirect:/wishitems";
    }
}

