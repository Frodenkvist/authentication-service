package com.authenticationservice;

import com.authenticationservice.common.model.Person;
import com.authenticationservice.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class PersonRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void findPersonByPersonnummer() {
        Person person1 = new Person("19900101-0001", Collections.emptyList());
        Person person2 = new Person("19900101-0002", Collections.emptyList());
        Person person3 = new Person("19900101-0003", Collections.emptyList());
        Person person4 = new Person("19900101-0004", Collections.emptyList());

        entityManager.persist(person1);
        entityManager.persist(person2);
        entityManager.persist(person3);
        entityManager.persist(person4);

        Optional<Person> foundPerson = personRepository.findPersonByPersonnummer(person2.getPersonnummer());

        assertTrue(foundPerson.isPresent());
        assertEquals(person2, foundPerson.get());
    }

    @Test
    public void findPersonMissingByPersonnummer() {
        Person person1 = new Person("19900101-0001", Collections.emptyList());
        Person person2 = new Person("19900101-0002", Collections.emptyList());
        Person person3 = new Person("19900101-0003", Collections.emptyList());
        Person person4 = new Person("19900101-0004", Collections.emptyList());

        entityManager.persist(person1);
        entityManager.persist(person2);
        entityManager.persist(person3);
        entityManager.persist(person4);

        Optional<Person> foundPerson = personRepository.findPersonByPersonnummer("19900101-0005");

        assertFalse(foundPerson.isPresent());
    }

    @Test
    public void deletePersonByPersonnummer() {
        Person person1 = new Person("19900101-0001", Collections.emptyList());
        Person person2 = new Person("19900101-0002", Collections.emptyList());
        Person person3 = new Person("19900101-0003", Collections.emptyList());
        Person person4 = new Person("19900101-0004", Collections.emptyList());

        entityManager.persist(person1);
        entityManager.persist(person2);
        entityManager.persist(person3);
        entityManager.persist(person4);

        Long deletedRows = personRepository.deletePersonByPersonnummer(person2.getPersonnummer());

        assertEquals(1L, deletedRows.longValue());
        assertNull(entityManager.find(Person.class, person2.getPersonnummer()));
    }

    @Test
    public void deleteMissingPersonByPersonnummer() {
        Person person1 = new Person("19900101-0001", Collections.emptyList());
        Person person2 = new Person("19900101-0002", Collections.emptyList());
        Person person3 = new Person("19900101-0003", Collections.emptyList());
        Person person4 = new Person("19900101-0004", Collections.emptyList());

        entityManager.persist(person1);
        entityManager.persist(person2);
        entityManager.persist(person3);
        entityManager.persist(person4);

        Long deletedRows = personRepository.deletePersonByPersonnummer("19900101-0005");

        assertEquals(0L, deletedRows.longValue());
    }
}
