package com.epam.capstone.service;

import com.epam.capstone.model.*;
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

    public List<Purchase> getAllItems(Person person) {
        return person.getPurchases();
    }

    @Transactional
    public void updatePerson(Person person, Person updatedPerson) {
        person.setName(updatedPerson.getName());
        person.setSurname(updatedPerson.getSurname());
        person.setEmail(updatedPerson.getEmail());

        peopleRepository.save(person);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }
}
