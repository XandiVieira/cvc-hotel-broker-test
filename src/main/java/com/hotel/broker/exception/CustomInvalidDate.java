package com.hotel.broker.exception;

public class CustomInvalidDate extends CustomException {

    public CustomInvalidDate(String message) {
        super(message);
    }

    public CustomInvalidDate(String message, Throwable cause) {
        super(message, cause);
    }
}