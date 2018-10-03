package com.authenticationservice.service;

import com.authenticationservice.common.exception.PersonMissingException;
import com.authenticationservice.common.model.Person;
import com.authenticationservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person getPerson(String personnummer) throws PersonMissingException {
        return personRepository.findPersonByPersonnummer(personnummer).orElseThrow(() ->
                new PersonMissingException("Unable to find person with personnummer: " + personnummer, personnummer));
    }
}
