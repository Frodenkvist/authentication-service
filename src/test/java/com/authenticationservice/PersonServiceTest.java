package com.authenticationservice;

import com.authenticationservice.common.exception.PersonMissingException;
import com.authenticationservice.common.model.Person;
import com.authenticationservice.repository.PersonRepository;
import com.authenticationservice.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PersonServiceTest {
    @TestConfiguration
    static class PersonServiceTestContextConfiguration {
        @Bean
        public PersonService personService() {
            return new PersonService();
        }
    }

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Test
    public void getPerson() throws PersonMissingException {
        Person person = new Person("19900101-0001", Collections.emptyList());

        when(personRepository.findPersonByPersonnummer(person.getPersonnummer())).thenReturn(Optional.of(person));

        Person returnPerson = personService.getPerson(person.getPersonnummer());

        assertEquals(person, returnPerson);
    }

    @Test(expected = PersonMissingException.class)
    public void getMissingPerson() throws PersonMissingException {
        when(personRepository.findPersonByPersonnummer("19900101-0001")).thenReturn(Optional.empty());
        personService.getPerson("19900101-0001");
    }

    @Test
    public void deletePerson() throws PersonMissingException {
        when(personRepository.deletePersonByPersonnummer("19900101-0001")).thenReturn(1L);

        personService.deletePerson("19900101-0001");
    }

    @Test(expected = PersonMissingException.class)
    public void deleteMissingPerson() throws PersonMissingException {
        when(personRepository.deletePersonByPersonnummer("19900101-0001")).thenReturn(0L);

        personService.deletePerson("19900101-0001");
    }
}
