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
    @PostMapping("tasks/index")
    public String viewTasks(){
        return "redirect:/tasks/index";
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

    @GetMapping("/tasks/create")
    public String showCreateForm(Model model) {
        model.addAttribute("newTask", new Task());
//        model.addAttribute("status", statusDao.findAll());
        return "tasks/create";
    }

    @PostMapping("/tasks/create")
    public String submitCreateForm(@ModelAttribute Task newTask) {
        User taskUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newTask.setUser(taskUser);
//        Adds a status of 1 to the task
        newTask.setStatus(statusDao.getById(1L));
        taskDao.save(newTask);
        return "redirect:/tasks/index";
    }

    @GetMapping("/tasks/{id}/edit")
    public String showEditTaskForm(@PathVariable long id, Model model) {
        Task task = taskDao.getById(id);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == task.getUser().getId()) {
            model.addAttribute("task", task);

            return "tasks/edit";
        } else {
            return "redirect:/tasks/index";
        }
    }
    @PostMapping("/tasks/{id}/edit")
    public String submitEditTask(@ModelAttribute Task task, @PathVariable long id, @RequestParam (name = "imgurl") String imgurl ) {
//        if (taskDao.getById(id).getUser().getId() == ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
//            tasktoEdit.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
           task.setUser(usersDao.getById(1L));
        task.setIcon(imgurl);
            taskDao.save(task);
        return "redirect:/tasks/index";
    }



    @GetMapping("/tasks/{id}/delete")
    public String delete(@PathVariable long id) {
        taskDao.deleteById(id);
        return "redirect:/tasks";
    }

}


