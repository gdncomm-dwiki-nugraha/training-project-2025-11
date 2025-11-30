package com.training.cart.domain.exception;

import java.util.UUID;

/**
 * Exception thrown when a requested cart item (product) is not found in the
 * cart.
 */
public class CartItemNotFoundException extends RuntimeException {

    public CartItemNotFoundException(UUID productId) {
        super("Cart item with product ID '" + productId + "' not found in cart");
    }

    public CartItemNotFoundException(String message) {
        super(message);
    }

    public CartItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
