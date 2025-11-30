package com.training.member.domain.exception;

/**
 * Exception thrown when authentication credentials are invalid
 * (wrong email or password).
 */
public class InvalidCredentialException extends RuntimeException {

    public InvalidCredentialException() {
        super("Invalid email or password");
    }

    public InvalidCredentialException(String message) {
        super(message);
    }

    public InvalidCredentialException(String message, Throwable cause) {
        super(message, cause);
    }
}
