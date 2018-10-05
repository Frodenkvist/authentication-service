package com.authenticationservice;

import com.authenticationservice.common.exception.PermissionMissingException;
import com.authenticationservice.common.exception.PersonMissingException;
import com.authenticationservice.common.model.Permission;
import com.authenticationservice.common.model.PermissionName;
import com.authenticationservice.common.model.Person;
import com.authenticationservice.repository.PermissionRepository;
import com.authenticationservice.service.PermissionService;
import com.authenticationservice.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PermissionServiceTest {
    @TestConfiguration
    static class PermissionServiceTestContextConfiguration {
        @Bean
        public PermissionService permissionService() {
            return new PermissionService();
        }
    }

    @Autowired
    private PermissionService permissionService;

    @MockBean
    private PermissionRepository permissionRepository;

    @MockBean
    private PersonService personService;

    @Test
    public void createPermission() throws PersonMissingException {
        Person person = new Person("19900101-0001", Collections.emptyList());

        when(personService.getPerson(person.getPersonnummer())).thenReturn(person);
        doAnswer((Answer<Permission>) invocation -> {
            Permission argument = invocation.getArgument(0);
            argument.setId(0L);
            return argument;
        }).when(permissionRepository).save(any(Permission.class));

        Permission permission = permissionService.createPermission(person.getPersonnummer(), PermissionName.CREATE);

        assertNotNull(permission);
        assertEquals(0L, permission.getId().longValue());
        assertEquals(PermissionName.CREATE, permission.getName());
        assertEquals(person, permission.getPerson());
    }

    @Test(expected = PersonMissingException.class)
    public void createPermissionMissingPerson() throws PersonMissingException {

        when(personService.getPerson("19900101-0001")).thenThrow(PersonMissingException.class);

        permissionService.createPermission("19900101-0001", PermissionName.CREATE);
    }

    @Test
    public void deletePermission() throws PermissionMissingException {
        when(permissionRepository.deletePermissionByPersonnummerAndName("19900101-0001", PermissionName.CREATE)).thenReturn(1);

        permissionService.deletePermission("19900101-0001", PermissionName.CREATE);
    }

    @Test(expected = PermissionMissingException.class)
    public void deleteMissingPermission() throws PermissionMissingException {
        when(permissionRepository.deletePermissionByPersonnummerAndName("19900101-0001", PermissionName.CREATE)).thenReturn(0);

        permissionService.deletePermission("19900101-0001", PermissionName.CREATE);
    }
}
