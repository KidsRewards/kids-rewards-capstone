package com.codeup.kidsrewardscapstone.repositories;

import com.codeup.kidsrewardscapstone.models.Status;
import com.codeup.kidsrewardscapstone.models.User;
import com.codeup.kidsrewardscapstone.models.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishItemRepository extends JpaRepository<WishItem, Long> {
//    Status getById(long id);
    List<WishItem> findByUser(User user);
}
