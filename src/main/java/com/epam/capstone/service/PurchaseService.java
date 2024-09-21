package com.epam.capstone.service;

import com.epam.capstone.model.Cart;
import com.epam.capstone.model.CartItem;
import com.epam.capstone.model.Item;
import com.epam.capstone.model.Person;
import com.epam.capstone.utill.InsufficientFundsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    private final PeopleService peopleService;
    private final ItemsService itemsService;

    @Autowired
    public PurchaseService(PeopleService peopleService, ItemsService itemsService) {
        this.peopleService = peopleService;
        this.itemsService = itemsService;
    }

    @Transactional
    public void buy(Person person, Cart cart) {
        Double totalCost = cart.getItems().stream().mapToDouble(x -> x.getItem().getPrice() * x.getQuantity()).sum();

        if(person.getBalance() >= totalCost) {
            person.setBalance(person.getBalance() - totalCost);
            person.getItems().addAll(cart.getItems().stream().map(CartItem::getItem).toList());

            List<Item> itemsFromCart = cart.getItems().stream().map(CartItem::getItem).toList();
            itemsFromCart.forEach(x -> x.setPeople(List.of(person)));

            peopleService.save(person);
            itemsFromCart.forEach(itemsService::updateItem);
        } else {
            throw new InsufficientFundsException();
        }
    }
}
