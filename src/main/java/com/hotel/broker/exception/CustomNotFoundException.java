package com.hotel.broker.exception;

public class CustomNotFoundException extends CustomException {

    public CustomNotFoundException(String message) {
        super(message);
    }

    public CustomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}