package com.epam.capstone.service;

import com.epam.capstone.model.*;
import com.epam.capstone.repository.ItemsRepository;
import com.epam.capstone.repository.PeopleRepository;
import com.epam.capstone.repository.PurchaseRepository;
import com.epam.capstone.utill.InsufficientFundsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PeopleRepository peopleRepository;
    private final ItemsRepository itemsRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, PeopleRepository peopleRepository, ItemsRepository itemsRepository) {
        this.purchaseRepository = purchaseRepository;
        this.peopleRepository = peopleRepository;
        this.itemsRepository = itemsRepository;
    }


    @Transactional
    public void buy(Person person, Cart cart) {
        Double totalCost = cart.getItems().stream().mapToDouble(x -> x.getItem().getPrice() * x.getQuantity()).sum();

        if(person.getBalance() >= totalCost) {
            person.setBalance(person.getBalance() - totalCost);

            List<Purchase> purchases = cart.getItems().stream().map(
                    cartItem -> new Purchase(person, cartItem.getItem(), cartItem.getQuantity(), LocalDateTime.now())
            ).toList();

            purchaseRepository.saveAll(purchases);
            peopleRepository.save(person);
        } else {
            throw new InsufficientFundsException();
        }
    }


}
