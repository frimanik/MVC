package com.example.MVC_ObjectMapper.Exceptions;

public class ProductWasNotFoundException extends RuntimeException{
    public ProductWasNotFoundException(String message) {
        super(message);
    }

    public ProductWasNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
