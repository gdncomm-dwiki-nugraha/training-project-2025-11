package com.training.cart.application.dto;

import java.util.List;
import java.util.UUID;

/**
 * CartResponse DTO - Response payload for cart data.
 * Uses Java record for immutability.
 */
public record CartResponse(
        UUID userId,
        List<CartItemResponse> items) {
}
