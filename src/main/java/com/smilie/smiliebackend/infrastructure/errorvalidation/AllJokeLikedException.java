package com.smilie.smiliebackend.infrastructure.errorvalidation;

public class AllJokeLikedException extends RuntimeException {
    public AllJokeLikedException(String message) {
        super(message);
    }
}
