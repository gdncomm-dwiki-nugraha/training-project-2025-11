package com.training.apigateway.domain.exception;

/**
 * Exception thrown when a JWT token is invalid, expired, or malformed.
 */
public class InvalidJwtException extends RuntimeException {

    public InvalidJwtException(String message) {
        super(message);
    }

    public InvalidJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
