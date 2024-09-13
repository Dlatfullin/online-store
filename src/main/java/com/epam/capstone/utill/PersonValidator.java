package com.epam.capstone.utill;

import com.epam.capstone.model.Person;
import com.epam.capstone.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        Optional<Person> personOptional =  peopleService.getPersonByUsername(person.getUsername());
        if(personOptional.isPresent()){
            errors.rejectValue("username", "", "Username already exists");
        } else {
            return;
        }
    }

}
