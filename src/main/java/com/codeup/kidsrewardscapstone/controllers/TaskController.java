package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Reward;
import com.codeup.kidsrewardscapstone.models.Status;
import com.codeup.kidsrewardscapstone.models.Task;
import com.codeup.kidsrewardscapstone.models.User;
import com.codeup.kidsrewardscapstone.repositories.StatusRepository;
import com.codeup.kidsrewardscapstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codeup.kidsrewardscapstone.repositories.TaskRepository;

@Controller
public class TaskController {
    //added status repo and constructor
    private StatusRepository statusDao;
    private TaskRepository taskDao;
    private UserRepository usersDao;

    public TaskController(StatusRepository statusDao, TaskRepository taskDao, UserRepository usersDao) {
        this.statusDao = statusDao;
        this.taskDao = taskDao;
        this.usersDao = usersDao;
    }

    @GetMapping("tasks/index")
    public String viewTasks(Model model) {
        model.addAttribute("allTasks", taskDao.findAll());
        return "tasks/index";
    }

    //
//    @GetMapping("/tasks/{id}")
//    public String taskDetails(@PathVariable long id, Model model) {
//        Task task = taskDao.getById(id);
//        boolean isTaskOwner = false;
//        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser"){
//            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            isTaskOwner = loggedInUser.getId() == task.getUser().getId();
//        }
//        model.addAttribute("singleTask", taskDao.getById(id));
//        model.addAttribute("isTaskOwner", isTaskOwner);
//        return "tasks/show";
//    }
//    @GetMapping("/tasks/{id}")
//    public String taskDetails(@PathVariable long id, Model model) {
//        model.addAttribute("singleTask", taskDao.getById(id));
//        return "tasks/show";
//    }
    @GetMapping("/tasks/{id}")
    public String singleTask(@PathVariable long id, Model model){
        Task task = taskDao.getById(id);
        boolean isTaskOwner = false;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isTaskOwner = loggedInUser.getId() == task.getUser().getId();
        }
        model.addAttribute("singleTask", taskDao.getById(id));
        model.addAttribute("isTaskOwner", isTaskOwner);
            return "tasks/show";
        }


    //Learning that this was "ambiguous". i.e. more than 1 duplicate mapping
//    @GetMapping("/tasks")
//    public String showTasks(Model model) {
//        model.addAttribute("task", new Task());
//        return "redirect:task/tasks";
//    }
    @GetMapping("/tasks/create")
    public String showCreateForm(Model model) {
        model.addAttribute("newTask", new Task());
        return "tasks/create";
    }

    @PostMapping("/tasks/create")
    public String submitCreateForm(@ModelAttribute Task newTask) {
        User taskUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newTask.setUser(taskUser);
//        model.addAttribute("status", statusId);
        taskDao.save(newTask);
        return "redirect:/index";
    }

    @GetMapping("/tasks/{id}/edit")
    public String showEditTaskForm(@PathVariable long id, Model model) {
        Task task = taskDao.getById(id);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == task.getUser().getId()) {
            model.addAttribute("task", task);
            return "tasks/edit";
        } else {
            return "redirect:/tasks/";
        }
    }
    @PostMapping("/tasks/{id}/edit")
    public String submitEditTask(@ModelAttribute Task task, @PathVariable long id) {
//        if (taskDao.getById(id).getUser().getId() == ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
//            tasktoEdit.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
           task.setUser(usersDao.getById(1L));
            taskDao.save(task);
        return "redirect:/tasks";
    }


    @GetMapping("/tasks/{id}/delete")
    public String delete(@PathVariable long id) {
        taskDao.deleteById(id);
        return "redirect:/tasks";
    }

}


//        return "redirect:/status";@GetMapping("/statuses/{id}/edit")
//        public String showEditStatus(@PathVariable long id, Model model) {
//            Status statusToEdit = statusDao.getById(id);
//            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if (statusToEdit.getUser().getId() == loggedInUser.getId()) {
//                model.addAttribute("statusToEdit", statusToEdit);
//                return "statuses/edit";
//            } else {
//                return "redirect:/statuses";//?? or redirect tasks??
//            }
//        }
//
//        //to show the edit of status?
//        @PostMapping("/statuses/{id}/edit")
//        public String submitEdit(@ModelAttribute Status statusToEdit, @Path
//    }
//}

