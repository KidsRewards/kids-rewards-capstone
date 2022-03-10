package com.codeup.kidsrewardscapstone.repositories;

import com.codeup.kidsrewardscapstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}