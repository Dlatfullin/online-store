package com.epam.capstone.controller;

import com.epam.capstone.model.Person;
import com.epam.capstone.service.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final PeopleService peopleService;

    @Autowired
    public ProfileController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleService.getPersonByUsername(authentication.getName()).get();
        model.addAttribute("person", person);

        return "user/profile";
    }

    @PutMapping("/update")
    public String updateProfile(@ModelAttribute("person") @Valid Person person,
                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "user/profile";
        }

        peopleService.updatePerson(person);

        return "user/profile";

    }

    @GetMapping("/history")
    public String purchaseHistory(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleService.getPersonByUsername(authentication.getName()).get();

        model.addAttribute("items", peopleService.getAllItems(person));
        return "user/history";
    }
}
