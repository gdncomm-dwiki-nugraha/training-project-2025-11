package com.training.product.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * ProductResponse DTO - Response payload containing product data.
 * Uses Java record for immutability.
 */
public record ProductResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        String category) {
}
