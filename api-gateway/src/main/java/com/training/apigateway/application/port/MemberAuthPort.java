package com.training.apigateway.application.port;

import java.util.UUID;

/**
 * MemberAuthPort - Port (interface) for communicating with Member service.
 * This is an abstraction that will be implemented in the infrastructure layer.
 * Pure Java interface with NO Spring annotations.
 */
public interface MemberAuthPort {

    /**
     * Verify member credentials with the Member service.
     *
     * @param email    the member email
     * @param password the member password
     * @return the member's UUID if credentials are valid
     * @throws com.training.apigateway.domain.exception.InvalidJwtException if
     *                                                                      credentials
     *                                                                      are
     *                                                                      invalid
     */
    UUID verifyCredentials(String email, String password);
}
