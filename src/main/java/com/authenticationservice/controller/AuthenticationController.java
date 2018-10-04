package com.authenticationservice.controller;

import com.authenticationservice.common.exception.PermissionMissingException;
import com.authenticationservice.common.exception.PersonMissingException;
import com.authenticationservice.common.model.PermissionName;
import com.authenticationservice.controller.model.PermissionDTO;
import com.authenticationservice.service.PermissionService;
import com.authenticationservice.service.PersonService;
import com.common.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/authentication/permission")
public class AuthenticationController {
    @Autowired
    private PersonService personService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/{personnummer}")
    @ResponseBody
    public ResponseEntity<?> getPermissions(@PathVariable String personnummer) {
        try {
            return JSON.message(HttpStatus.OK, personService.getPerson(personnummer).getPermissions());
        } catch (PersonMissingException e) {
            return JSON.message(HttpStatus.NOT_FOUND, "Unable to find person with personnummer: " + personnummer);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> addPermission(@RequestBody @Valid PermissionDTO input) {
        try {
            return JSON.message(HttpStatus.OK, permissionService.createPermission(input.getPersonnummer(), input.getPermission()));
        } catch (PersonMissingException e) {
            return JSON.message(HttpStatus.NOT_FOUND, "Unable to find person with personnummer: " + input.getPersonnummer());
        }
    }

    @DeleteMapping("/{personnummer}/permission/{permissionName}")
    @ResponseBody
    public ResponseEntity<?> deletePermission(@PathVariable String personnummer, @PathVariable PermissionName permissionName) {
        try {
            permissionService.deletePermission(personnummer, permissionName);
            return JSON.message(HttpStatus.OK, "Permission deleted");
        } catch (PermissionMissingException e) {
            return JSON.message(HttpStatus.NOT_FOUND, "Unable to find permission with name: " + permissionName.name());
        }
    }
}
