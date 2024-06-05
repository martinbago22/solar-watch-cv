package com.codecool.solarwatch.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String userName) {
        super(String.format("Username: [ %s ] is already in use", userName));
    }
}
