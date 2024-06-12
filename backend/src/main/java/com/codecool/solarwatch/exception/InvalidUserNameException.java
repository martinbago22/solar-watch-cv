package com.codecool.solarwatch.exception;

public class InvalidUserNameException extends RuntimeException {
    public InvalidUserNameException() {
        super("Username must be between 2 and 8 characters long and cannot contain any special characters or whitespaces");
    }
}
