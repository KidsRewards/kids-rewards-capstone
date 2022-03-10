package com.codeup.kidsrewardscapstone.repositories;

import com.codeup.kidsrewardscapstone.models.Reward;
import com.codeup.kidsrewardscapstone.models.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishItemRepository extends JpaRepository<WishItem, Long> {
}