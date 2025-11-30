package com.training.apigateway.application.usecase;

import com.training.apigateway.application.dto.LoginRequest;
import com.training.apigateway.application.dto.LoginResponse;
import com.training.apigateway.application.port.MemberAuthPort;
import com.training.apigateway.domain.model.AuthUser;
import com.training.apigateway.domain.service.JwtProvider;

import java.util.UUID;

/**
 * AuthenticateUserUseCase - Application service for user authentication.
 * Pure Java class with NO Spring annotations.
 * Uses constructor injection for dependencies.
 */
public class AuthenticateUserUseCase {

    private final MemberAuthPort memberAuthPort;
    private final JwtProvider jwtProvider;

    public AuthenticateUserUseCase(MemberAuthPort memberAuthPort, JwtProvider jwtProvider) {
        this.memberAuthPort = memberAuthPort;
        this.jwtProvider = jwtProvider;
    }

    /**
     * Execute user authentication.
     * Calls member service to verify credentials, then generates JWT if valid.
     *
     * @param request the login request
     * @return LoginResponse with JWT token
     * @throws com.training.apigateway.domain.exception.InvalidJwtException if
     *                                                                      credentials
     *                                                                      are
     *                                                                      invalid
     */
    public LoginResponse execute(LoginRequest request) {
        // Verify credentials via member service
        UUID userId = memberAuthPort.verifyCredentials(request.email(), request.password());

        // Create AuthUser
        AuthUser authUser = new AuthUser(userId);

        // Generate JWT token
        String token = jwtProvider.generate(authUser);

        // Return response
        return new LoginResponse(token);
    }
}
