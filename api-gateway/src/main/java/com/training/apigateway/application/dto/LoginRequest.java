package com.training.apigateway.application.dto;

/**
 * LoginRequest DTO - Request payload for user authentication.
 * Uses Java record for immutability.
 */
public record LoginRequest(
        String email,
        String password) {
}
