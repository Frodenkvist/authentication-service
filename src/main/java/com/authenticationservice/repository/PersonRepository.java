package com.authenticationservice.repository;

import com.authenticationservice.common.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, String> {
    Optional<Person> findPersonByPersonnummer(String personnummer);
    @Modifying
    @Transactional
    Long deletePersonByPersonnummer(String personnummer);
}
