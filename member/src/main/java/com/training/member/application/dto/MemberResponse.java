package com.training.member.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * MemberResponse DTO - Response payload containing member data.
 * Uses Java record for immutability.
 */
public record MemberResponse(
        UUID id,
        String email,
        LocalDateTime createdAt) {
}
