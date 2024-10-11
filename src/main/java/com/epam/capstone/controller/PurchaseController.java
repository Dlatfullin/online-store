package com.epam.capstone.controller;

import com.epam.capstone.model.Cart;
import com.epam.capstone.model.Person;
import com.epam.capstone.service.PeopleService;
import com.epam.capstone.service.PurchaseService;
import com.epam.capstone.utill.InsufficientFundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PeopleService peopleService;
    private final Cart cart;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, PeopleService peopleService, Cart cart) {
        this.purchaseService = purchaseService;
        this.peopleService = peopleService;
        this.cart = cart;
    }

    // здесб ошибки
    @PatchMapping("/buy")
    public String buy() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleService.getPersonByUsername(authentication.getName()).get();


        try {
            purchaseService.buy(person, cart);
            cart.getItems().clear();
        } catch (InsufficientFundsException e) {
            return "redirect:/cart?errorMessage";
        }

        return "redirect:/cart";
    }
}
