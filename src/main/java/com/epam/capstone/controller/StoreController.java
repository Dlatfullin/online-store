package com.epam.capstone.controller;

import com.epam.capstone.model.Person;
import com.epam.capstone.service.ItemsService;
import com.epam.capstone.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/store")
public class StoreController {
    private final PeopleService peopleService;
    private final ItemsService itemsService;

    @Autowired
    public StoreController(PeopleService peopleService, ItemsService itemsService) {
        this.peopleService = peopleService;
        this.itemsService = itemsService;
    }

    @GetMapping
    public String getItems(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("items", itemsService.getAllItems());

        if (isAuthenticated) {
            Person person = peopleService.getPersonByUsername(authentication.getName()).get();
            model.addAttribute("person", person);
        }

        return "store/index";
    }

    @GetMapping("/{id}")
    public String getItem(@PathVariable long id, Model model) {
        model.addAttribute("item", itemsService.getItemById(id));
        return "store/item";
    }
}
