package com.training.member.application.dto;

/**
 * LoginRequest DTO - Request payload for member login.
 * Uses Java record for immutability.
 */
public record LoginRequest(
        String email,
        String password) {
}
