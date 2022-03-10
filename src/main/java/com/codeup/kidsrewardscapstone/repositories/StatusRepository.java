package com.codeup.kidsrewardscapstone.repositories;

import com.codeup.kidsrewardscapstone.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findById(long id);
}
