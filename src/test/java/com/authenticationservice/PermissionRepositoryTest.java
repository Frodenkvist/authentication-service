package com.authenticationservice;

import com.authenticationservice.common.model.Permission;
import com.authenticationservice.common.model.PermissionName;
import com.authenticationservice.common.model.Person;
import com.authenticationservice.repository.PermissionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class PermissionRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    public void deletePermissionByPersonAndName() {
        Person person = new Person("19900101-0001", Collections.emptyList());

        entityManager.persist(person);

        Permission permission1 = new Permission();
        permission1.setName(PermissionName.SEARCH);
        permission1.setPerson(person);
        entityManager.persist(permission1);

        Permission permission2 = new Permission();
        permission2.setName(PermissionName.CREATE);
        permission2.setPerson(person);
        entityManager.persist(permission2);

        Permission permission3 = new Permission();
        permission3.setName(PermissionName.SYSADMIN);
        permission3.setPerson(person);
        entityManager.persist(permission3);

        int deletedRows = permissionRepository.deletePermissionByPersonnummerAndName(person.getPersonnummer(), PermissionName.CREATE);

        assertEquals(1, deletedRows);
    }
}
