package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.*;
import com.codeup.kidsrewardscapstone.repositories.FamilyRepository;
import com.codeup.kidsrewardscapstone.repositories.StatusRepository;
import com.codeup.kidsrewardscapstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${FILESTACK_API_KEY}")
    private String fileStackApiKey;


    public TaskController(StatusRepository statusDao, TaskRepository taskDao, UserRepository usersDao, FamilyRepository familiesDao) {
        this.statusDao = statusDao;
        this.taskDao = taskDao;
        this.usersDao = usersDao;
        this.familiesDao = familiesDao;
    }

//    Display tasks assigned to the child
    @GetMapping("tasks/index")
    public String viewTasks(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = loggedInUser.getId();
        System.out.println(id);

        model.addAttribute("allTasks", taskDao.findAllByUserId(id));
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

//        Shows the task create form and allows the ability to select from a list of children to assign the task to
    @GetMapping("/tasks/create")
    public String showCreateForm(Model model) {
        model.addAttribute("newTask", new Task());
        model.addAttribute("fsKey", fileStackApiKey);

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

  //      newTask.setUser(taskUser);
//        Adds a status of 1 to the task
   //     System.out.println(newTask.getIcon());
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


