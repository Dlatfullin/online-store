package com.epam.capstone.controller;

import com.epam.capstone.model.Cart;
import com.epam.capstone.model.Person;
import com.epam.capstone.service.PeopleService;
import com.epam.capstone.service.PurchaseService;
import com.epam.capstone.utill.InsufficientFundsException;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PeopleService peopleService;

    public PurchaseController(PurchaseService purchaseService, PeopleService peopleService) {
        this.purchaseService = purchaseService;
        this.peopleService = peopleService;
    }

    // здесб ошибки
    @PatchMapping("/buy")
    public String buy(HttpSession session, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleService.getPersonByUsername(authentication.getName()).get();

        Cart cart = (Cart) session.getAttribute("cart");

        try {
            purchaseService.buy(person, cart);
            session.setAttribute("cart", null);
        } catch (InsufficientFundsException e) {
            return "redirect:/cart?errorMessage";
        }

        return "redirect:/cart";
    }
}
