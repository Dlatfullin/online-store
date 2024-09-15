package com.epam.capstone.service;

import com.epam.capstone.model.Cart;
import com.epam.capstone.model.CartItem;
import com.epam.capstone.model.Item;
import com.epam.capstone.model.Person;
import com.epam.capstone.repository.PeopleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Optional<Person> getPersonByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }

    public Optional<Person> getPersonById(Long id) {
        return peopleRepository.findById(id);
    }

    // вопрос
    @Transactional
    public List<CartItem> getAllItems(Person person) {
        List<Item> items = person.getItems();
        Map<Item, Integer> itemCountMap = new HashMap<>();

        for (Item item : items) {
            itemCountMap.put(item, itemCountMap.getOrDefault(item, 0) + 1);
        }

        List<CartItem> cartItems = new ArrayList<>();
        for (Map.Entry<Item, Integer> entry : itemCountMap.entrySet()) {
            cartItems.add(new CartItem(entry.getKey(), entry.getValue()));
        }

        return cartItems;
    }

    @Transactional
    public void updatePerson(Person person) {
        peopleRepository.save(person);
    }
}
