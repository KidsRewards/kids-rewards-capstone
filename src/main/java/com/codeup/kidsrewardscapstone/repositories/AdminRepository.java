package com.codeup.kidsrewardscapstone.repositories;

import com.codeup.kidsrewardscapstone.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}
