package com.authenticationservice.controller;

import com.authenticationservice.common.exception.PersonMissingException;
import com.authenticationservice.controller.util.JS;
import com.authenticationservice.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/authentication")
public class AuthenticationController {
    @Autowired
    private PersonService personService;

    @GetMapping("/permission/{personnummer}")
    public ResponseEntity<?> getPermissions(@PathVariable String personnummer) {
        try {
            return JS.message(HttpStatus.OK, personService.getPerson(personnummer).getPermissions());
        } catch (PersonMissingException e) {
            return JS.message(HttpStatus.NOT_FOUND, "Unable to find person with personnummer: " + personnummer);
        }
    }
}
