package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.*;
import com.codeup.kidsrewardscapstone.repositories.FamilyRepository;
import com.codeup.kidsrewardscapstone.repositories.StatusRepository;
import com.codeup.kidsrewardscapstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codeup.kidsrewardscapstone.repositories.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {
    //added status repo and constructor
    private StatusRepository statusDao;
    private TaskRepository taskDao;
    private UserRepository usersDao;
    private FamilyRepository familiesDao;

    public TaskController(StatusRepository statusDao, TaskRepository taskDao, UserRepository usersDao, FamilyRepository familiesDao) {
        this.statusDao = statusDao;
        this.taskDao = taskDao;
        this.usersDao = usersDao;
        this.familiesDao = familiesDao;
    }

//    Displays tasks assigned to the child
    @GetMapping("tasks/index")
    public String viewTasks(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = loggedInUser.getId();
        System.out.println(id);

            model.addAttribute("allTasks", taskDao.findAllByUserId(id));
            model.addAttribute("user", loggedInUser);

        return "tasks/index";
    }

    @PostMapping("tasks/index")
    public String viewTasks(){
            return "redirect:/tasks/index";

    }

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

//Shows the task create form and allows the ability to select from a list of children to assign the task to
    @GetMapping("/tasks/create")
    public String showCreateForm(Model model) {
        model.addAttribute("newTask", new Task());

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Family currentFamily = familiesDao.findFamilyByUsers(loggedInUser);
        List<User> wholeFamily = usersDao.findUsersByFamilies(currentFamily);
        List<User> children = new ArrayList<>();

        for(User user : wholeFamily){
            if(!user.getParent()){
                System.out.println(user.getId());
                children.add(user);
            }
        }

        model.addAttribute("children", children);
        return "tasks/create";
    }

    @PostMapping("/tasks/create")
    public String submitCreateForm(@ModelAttribute Task newTask, @RequestParam(name="childId") long childId) {
        User taskUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        newTask.setUser(usersDao.getById(childId));

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

//    Once button is clicked, it changes the status as Review
    @GetMapping("/tasks/{id}/review")
    public String reviewChildTask(@PathVariable long id, Model model){
        Task taskToReview = taskDao.getById(id);
        taskToReview.setStatus(statusDao.getById(2L));
        taskDao.save(taskToReview);
        return "redirect:/tasks/index";
    }

//    Allows for a parent to view all tasks that need to be reviewed
    @GetMapping("tasks/reviewform")
    public String viewTasksToReview(Model model){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Family currentFamily = familiesDao.findFamilyByUsers(loggedInUser);
        List<User> wholeFamily = usersDao.findUsersByFamilies(currentFamily);
        List<User> children = new ArrayList<>();

        for(User user : wholeFamily){
            if(!user.getParent()){
                System.out.println(user.getId());
                children.add(user);
            }
        }

        List<Task> taskToReview = new ArrayList<>();

        for(User child : children){
            Long userId = child.getId();
            List<Task> childTasks = taskDao.findAllByUserId(userId);
            for(Task task : childTasks) {
                if (task.getStatus().getId() == 2) {
                    taskToReview.add(task);
                }
            }
        }
        model.addAttribute("allTasks", taskToReview);
        return "tasks/reviewform";
    }

//    @GetMapping("tasks/{id}/approved")
//    public String taskToApprove(@PathVariable long id, Model model){
//        Task currentTask = taskDao.getById(id);
//        model.addAttribute("")
//        return "tasks/reviewform";
//    }

    @GetMapping("tasks/{id}/approved")
    public String approveTask(@ModelAttribute Task taskApproved, @PathVariable long id){
        Task taskToApprove = taskDao.getById(id);

        User assignedUser = taskToApprove.getUser();
        Long addPoints = taskToApprove.getPoints();

        Long newPointTotal = assignedUser.getPointsTotal() + addPoints;

        assignedUser.setPointsTotal(newPointTotal);
        taskDao.delete(taskApproved);
        usersDao.save(assignedUser);
        return "redirect:/tasks/reviewform";
    }
}


