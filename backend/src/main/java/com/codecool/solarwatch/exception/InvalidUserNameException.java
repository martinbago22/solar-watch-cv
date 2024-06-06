package com.codecool.solarwatch.exception;

public class InvalidUserNameException extends RuntimeException {
    public InvalidUserNameException() {
        super("Username cannot contain any special characters");
    }
}
