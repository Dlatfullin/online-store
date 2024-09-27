package com.epam.capstone.repository;

import com.epam.capstone.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Long> {
    List<Item> findByTitleContaining(String title);
}
