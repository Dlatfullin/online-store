package com.epam.capstone.service;

import com.epam.capstone.model.Item;
import com.epam.capstone.repository.ItemsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemsService {
    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public List<Item> getAllItems() {
        return itemsRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateItem(Item item) {
        itemsRepository.save(item);
    }

    public List<Item> getSearch(String query) {
        return itemsRepository.findByTitleContaining(query);
    }
}
