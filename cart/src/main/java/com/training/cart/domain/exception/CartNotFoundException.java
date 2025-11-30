package com.training.cart.domain.exception;

import java.util.UUID;

/**
 * Exception thrown when a requested cart is not found.
 */
public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(UUID userId) {
        super("Cart for user ID '" + userId + "' not found");
    }

    public CartNotFoundException(String message) {
        super(message);
    }

    public CartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
