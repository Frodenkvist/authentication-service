package com.authenticationservice.common.exception;

public class PersonMissingException extends Exception {
    private String personNumber;

    public PersonMissingException(String message, String personNumber) {
        super(message);
        this.personNumber = personNumber;
    }

    public String getPersonNumber() {
        return personNumber;
    }
}
