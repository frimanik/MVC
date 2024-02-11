package com.example.MVC_ObjectMapper.Exceptions;

public class OrderWasNotFoundException extends RuntimeException{
    public OrderWasNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderWasNotFoundException(String message) {
        super(message);
    }
}
