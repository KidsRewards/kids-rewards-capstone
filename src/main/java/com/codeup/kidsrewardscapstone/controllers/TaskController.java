package com.codeup.kidsrewardscapstone.controllers;

import com.codeup.kidsrewardscapstone.models.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.codeup.kidsrewardscapstone.repositories.TaskRepository;

@Controller
public class TaskController {
    private TaskRepository taskDao;

    public TaskController(TaskRepository taskDao) {
        this.taskDao = taskDao;
    }

    @GetMapping("/tasks")
    public String showTasks(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/task";
    }
}
