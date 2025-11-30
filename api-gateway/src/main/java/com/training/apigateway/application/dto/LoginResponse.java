package com.training.apigateway.application.dto;

/**
 * LoginResponse DTO - Response payload containing JWT token.
 * Uses Java record for immutability.
 */
public record LoginResponse(
        String token) {
}
