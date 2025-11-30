package com.training.cart.application.dto;

import java.util.UUID;

/**
 * CartItemResponse DTO - Response payload for cart item data.
 * Uses Java record for immutability.
 */
public record CartItemResponse(
        UUID productId,
        int quantity) {
}
