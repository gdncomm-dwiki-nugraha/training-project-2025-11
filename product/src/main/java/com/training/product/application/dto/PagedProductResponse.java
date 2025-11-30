package com.training.product.application.dto;

import java.util.List;

/**
 * PagedProductResponse DTO - Response payload for paginated product list.
 * Uses Java record for immutability.
 */
public record PagedProductResponse(
        List<ProductResponse> items,
        int page,
        int size,
        long total) {
}
