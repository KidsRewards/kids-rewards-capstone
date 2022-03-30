package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Family;
import com.codeup.kidsrewardscapstone.models.User;
import com.codeup.kidsrewardscapstone.repositories.FamilyRepository;
import com.codeup.kidsrewardscapstone.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FamilyController {
    private FamilyRepository familiesDao;
    private UserRepository usersDao;
    private PasswordEncoder passwordEncoder;


    public FamilyController(FamilyRepository familiesDao, UserRepository usersDao, PasswordEncoder passwordEncoder){
        this.familiesDao = familiesDao;
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;

    }

    @GetMapping("/createfamily")
    public String showFamilyForm(Model model){
//        model.addAttribute("user", new User());
        model.addAttribute("family", new Family());
        return "users/createfamily";
    }

//    @PostMapping("/createfamily")
//    public String saveFamilyName(@ModelAttribute Family family){
//        User newParent = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        newParent = usersDao.getById(newParent.getId());
////        Family family = new Family(familyName);
//        List<Family> newFamily = new ArrayList<>();
//        if(newParent.getFamilies() != null){
//            newFamily = newParent.getFamilies();
//        }
//        List<User> users = new ArrayList<>();
//        users.add(newParent);
//        family.setUsers(users);
//        newFamily.add(family);
//        newParent.setFamilies(newFamily);
//        usersDao.save(newParent);
//        return "redirect:/index";
//    }


    @PostMapping("/createfamily")
    public String saveFamilyName(@RequestParam(name="name") String familyName){

        User newParent = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newParent = usersDao.getById(newParent.getId());
        Family family = new Family(familyName);
        List<Family> newFamily = new ArrayList<>();
        if(newParent.getFamilies() != null){
            newFamily = newParent.getFamilies();
        }
        List<User> users = new ArrayList<>();
        users.add(newParent);
        family.setUsers(users);
        newFamily.add(family);
        newParent.setFamilies(newFamily);
        usersDao.save(newParent);
        return "redirect:/createchild";
//        return "redirect:/index";
    }

//    Used to show child form
    @GetMapping("/createchild")
    public String showChildForm(Model model){
        model.addAttribute("user", new User());
        return "family/createchild";
    }

//    Adds new user to users table and hashes password, also adds them into the current logged in User family
    @PostMapping("/createchild")
    public String saveChildWithFamily(@ModelAttribute User user){
//        Retrieves logged in user_id and stores it in a variable
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Family family = familiesDao.findFamilyByUsers(loggedInUser);
        List<Family> addFamilyName = new ArrayList<>();
        addFamilyName.add(family);

        List<User> users = new ArrayList<>();
        users.add(user);
        family.setUsers(users);

        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setFamilies(addFamilyName);
        user.setParent(false);
        usersDao.save(user);
        return "redirect:/index";
    }
}
