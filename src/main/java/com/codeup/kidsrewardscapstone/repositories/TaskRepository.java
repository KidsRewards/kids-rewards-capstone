package com.codeup.kidsrewardscapstone.repositories;

import com.codeup.kidsrewardscapstone.models.Task;
import com.codeup.kidsrewardscapstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findById(long id);
    List<Task> findAllByUserId(long id);
    Task getAllByUserId(long id);
    List<Task> getAllByUser(User user);
   Task getByIcon (Icon icon);
}
