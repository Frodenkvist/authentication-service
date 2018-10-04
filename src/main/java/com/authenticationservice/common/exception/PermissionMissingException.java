package com.authenticationservice.common.exception;

public class PermissionMissingException extends Exception {
    public PermissionMissingException(String message) {
        super(message);
    }
}
