package com.epam.capstone.controller;

import com.epam.capstone.model.Person;
import com.epam.capstone.service.PeopleService;
import com.epam.capstone.utill.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/update")
    public String updateProfile(@ModelAttribute("person") @Validated(ValidationGroups.UpdateGroup.class) Person updatedPerson,
                                BindingResult bindingResult) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleService.getPersonByUsername(authentication.getName()).get();

        if(bindingResult.hasErrors()) {
            return "user/edit";
        }

        peopleService.updatePerson(person, updatedPerson);


        return "redirect:/profile";

    }

    @GetMapping("/history")
    public String purchaseHistory(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleService.getPersonByUsername(authentication.getName()).get();

        model.addAttribute("items", peopleService.getAllItems(person));
        return "user/history";
    }
}
