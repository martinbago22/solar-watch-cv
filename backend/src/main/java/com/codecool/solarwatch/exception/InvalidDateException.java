package com.codecool.solarwatch.exception;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException() {
        super("Date is in invalid format. Please provide it with YYYY-MM-DD");
    }
    public InvalidDateException(String message) {
        super(message);
    }
}
