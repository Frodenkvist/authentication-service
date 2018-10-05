package com.authenticationservice;

import com.authenticationservice.common.exception.PermissionMissingException;
import com.authenticationservice.common.exception.PersonMissingException;
import com.authenticationservice.common.model.Permission;
import com.authenticationservice.common.model.PermissionName;
import com.authenticationservice.common.model.Person;
import com.authenticationservice.controller.AuthenticationController;
import com.authenticationservice.controller.model.PermissionDTO;
import com.authenticationservice.service.PermissionService;
import com.authenticationservice.service.PersonService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
@ActiveProfiles("test")
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private PermissionService permissionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getPermissions() throws Exception {
        Person person = new Person("19900101-0001", Collections.emptyList());

        Permission permission1 = new Permission();
        permission1.setName(PermissionName.CREATE);

        Permission permission2 = new Permission();
        permission2.setName(PermissionName.SEARCH);

        Permission permission3 = new Permission();
        permission3.setName(PermissionName.SYSADMIN);

        person.setPermissions(Arrays.asList(permission1, permission2, permission3));

        when(personService.getPerson(person.getPersonnummer())).thenReturn(person);

        MvcResult result = mvc.perform(get("/v1/authentication/permission/" + person.getPersonnummer())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        List<String> permissions = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<String>>() {});

        assertNotNull(permissions);

        assertEquals(3, permissions.size());
        assertEquals(Arrays.asList(permission1.getName().name(), permission2.getName().name(), permission3.getName().name()),
                permissions);
    }

    @Test
    public void getPermissionsFromMissingPerson() throws Exception {
        when(personService.getPerson("19900101-0001")).thenThrow(PersonMissingException.class);

        mvc.perform(get("/v1/authentication/permission/19900101-0001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void addPermission() throws Exception {
        Person person = new Person("19900101-0001", Collections.emptyList());

        PermissionDTO input = new PermissionDTO(PermissionName.CREATE, person.getPersonnummer());
        Permission permission = new Permission();
        permission.setId(0L);
        permission.setName(input.getPermission());
        permission.setPerson(person);

        when(permissionService.createPermission(input.getPersonnummer(), input.getPermission())).thenReturn(permission);

        MvcResult result = mvc.perform(post("/v1/authentication/permission")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andReturn();

        Permission returnPermission = objectMapper.readValue(result.getResponse().getContentAsString(), Permission.class);

        assertNotNull(returnPermission);
        assertEquals(permission, returnPermission);
    }

    @Test
    public void addPermissionToMissingPerson() throws Exception {
        when(permissionService.createPermission("19900101-0001", PermissionName.CREATE)).thenThrow(PersonMissingException.class);

        PermissionDTO input = new PermissionDTO(PermissionName.CREATE, "19900101-0001");

        mvc.perform(post("/v1/authentication/permission")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void deletePermission() throws Exception {
        MvcResult result = mvc.perform(delete("/v1/authentication/permission/19900101-0001/permission/" + PermissionName.CREATE.name())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode message = objectMapper.readValue(result.getResponse().getContentAsString(), JsonNode.class);

        assertNotNull(message);
        assertNotNull(message.get("message"));
    }

    @Test
    public void deleteMissingPermission() throws Exception {
        doThrow(PermissionMissingException.class).when(permissionService).deletePermission("19900101-0001", PermissionName.CREATE);

        mvc.perform(delete("/v1/authentication/permission/19900101-0001/permission/" + PermissionName.CREATE.name())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }
}
