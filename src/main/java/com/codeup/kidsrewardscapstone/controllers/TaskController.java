//package com.codeup.kidsrewardscapstone.controllers;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import com.codeup.kidsrewardscapstone.repositories.TaskRepository;
//
//@Controller
//public class TaskController {
//    private TaskRepository taskDao;
//
//    public TaskController(TaskRepository taskDao){
//        this.taskDao = taskDao;
//    }
//
//    @GetMapping("/tasks")
//    public String showTasks(@PathVariable long id, Model model) {
//        model.addAttribute("task", taskDao.getById(id));
//        return "task";
//}
