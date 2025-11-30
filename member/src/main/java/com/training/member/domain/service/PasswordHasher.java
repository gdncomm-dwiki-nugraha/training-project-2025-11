package com.training.member.domain.service;

/**
 * PasswordHasher interface - Strategy Pattern implementation.
 * This allows different password hashing strategies (BCrypt, Argon2, etc.)
 * to be used interchangeably.
 * Pure Java interface with NO Spring annotations.
 */
public interface PasswordHasher {

    /**
     * Hash a raw password.
     *
     * @param rawPassword the plain text password
     * @return the hashed password
     */
    String hash(String rawPassword);

    /**
     * Check if a raw password matches the hashed password.
     *
     * @param rawPassword    the plain text password
     * @param hashedPassword the hashed password to compare against
     * @return true if passwords match, false otherwise
     */
    boolean matches(String rawPassword, String hashedPassword);
}
