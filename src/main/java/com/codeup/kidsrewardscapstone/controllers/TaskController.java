package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Status;
import com.codeup.kidsrewardscapstone.models.Task;
import com.codeup.kidsrewardscapstone.models.User;
import com.codeup.kidsrewardscapstone.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.codeup.kidsrewardscapstone.repositories.TaskRepository;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskController {
    //added status repo and constructor
    private StatusRepository statusDao;
    private TaskRepository taskDao;

    public TaskController(StatusRepository statusDao, TaskRepository taskDao) {
        this.statusDao = statusDao;
        this.taskDao = taskDao;
    }

    @GetMapping("/tasks")
    public String showTasks(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/task";
    }

}

//  _____________ Status ____________//


//
//@GetMapping("/statuses/{id}/edit")
//public String showEditStatus(@PathVariable long id, Model model) {
//    Status statusToEdit = statusDao.getById(id);
//    User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    if (statusToEdit.getUser().getId() == loggedInUser.getId()) {
//        model.addAttribute("statusToEdit", statusToEdit);
//        return "statuses/edit";
//    } else {
//        return "redirect:/status";//?? or redirect tasks??
//    }
//}
//    //to show the edit of status?
//    @PostMapping("/statuses/{id}/edit")
//    public String submitEdit(@ModelAttribute Status statusToEdit, @PathVariable long id) {
//        if (statusDao.getById(id).getUser().getId() == ((User) SecurityContextHolder.getContext()
//                .getAuthentication().getPrincipal()).getId()) {
//            statusToEdit.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//
//            statusDao.save(statusToEdit);
//        }
//        return "redirect:/status";
//    }
//}
