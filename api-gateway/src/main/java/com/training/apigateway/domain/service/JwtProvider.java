package com.training.apigateway.domain.service;

import com.training.apigateway.domain.model.AuthUser;

/**
 * JwtProvider interface - Provides JWT token generation and validation.
 * This is a pure Java interface with NO Spring annotations.
 * Different implementations can be created (HS256, RS256, etc.)
 */
public interface JwtProvider {

    /**
     * Generate a JWT token for the given authenticated user.
     *
     * @param user the authenticated user
     * @return JWT token as a string
     */
    String generate(AuthUser user);

    /**
     * Validate and parse a JWT token.
     *
     * @param token the JWT token string
     * @return the authenticated user extracted from the token
     * @throws com.training.apigateway.domain.exception.InvalidJwtException if token
     *                                                                      is
     *                                                                      invalid
     */
    AuthUser validate(String token);
}
