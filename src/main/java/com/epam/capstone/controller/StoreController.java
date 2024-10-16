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
import org.springframework.web.bind.annotation.*;

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
    public String getItems(@RequestParam(value = "query", required = false) String query,
                           Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        System.out.println(isAdmin);

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isAuthenticated", isAuthenticated);

        if(query != null) {
            model.addAttribute("items", itemsService.getSearch(query));
        } else {
            model.addAttribute("items", itemsService.getAllItems());
        }
        if (isAuthenticated) {
            Person person = peopleService.getPersonByUsername(authentication.getName()).get();
            model.addAttribute("person", person);
        }

        return "store/index";
    }

    @GetMapping("/{id}")
    public String getItem(@PathVariable long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("item", itemsService.getItemById(id));
        return "store/item";
    }

}
