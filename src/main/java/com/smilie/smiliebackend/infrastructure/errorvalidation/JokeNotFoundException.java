package com.smilie.smiliebackend.infrastructure.errorvalidation;

public class JokeNotFoundException extends RuntimeException {
    public JokeNotFoundException(String message) {
        super(message);
    }
}
