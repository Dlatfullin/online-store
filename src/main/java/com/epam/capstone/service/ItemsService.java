package com.epam.capstone.service;

import com.epam.capstone.model.Item;
import com.epam.capstone.repository.ItemsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateItem(Item item, Long id) {
        Item itemToUpdate = getItemById(id);
        itemToUpdate.setTitle(item.getTitle());
        itemToUpdate.setDescription(item.getDescription());
        itemToUpdate.setPrice(item.getPrice());
        itemToUpdate.setCategory(item.getCategory());
        itemsRepository.save(itemToUpdate);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createItem(Item item) {
        itemsRepository.save(item);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteItem(Long id) {
        itemsRepository.deleteById(id);
    }

    public List<Item> getSearch(String query) {
        return itemsRepository.findByTitleContaining(query);
    }
}
