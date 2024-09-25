package com.project.planetarium.exceptions;

public class AuthenticationFailed extends RuntimeException {
    public AuthenticationFailed(String message) {
        super(message);
    }
}
