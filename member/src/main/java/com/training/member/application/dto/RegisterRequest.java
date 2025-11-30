package com.training.member.application.dto;

/**
 * RegisterRequest DTO - Request payload for member registration.
 * Uses Java record for immutability.
 */
public record RegisterRequest(
        String email,
        String password) {
}
