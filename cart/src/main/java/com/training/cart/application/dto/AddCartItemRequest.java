package com.training.cart.application.dto;

import java.util.UUID;

/**
 * AddCartItemRequest DTO - Request payload for adding item to cart.
 * Uses Java record for immutability.
 */
public record AddCartItemRequest(
        UUID productId,
        int quantity) {
}
