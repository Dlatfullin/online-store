package com.epam.capstone.service;

import com.epam.capstone.model.Person;
import com.epam.capstone.repository.PeopleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Transactional
    public void updatePerson(Person person) {
        peopleRepository.save(person);
    }
}
