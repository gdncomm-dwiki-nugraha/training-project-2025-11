package com.training.product.domain.exception;

import java.util.UUID;

/**
 * Exception thrown when a requested product is not found.
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(UUID productId) {
        super("Product with ID '" + productId + "' not found");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
