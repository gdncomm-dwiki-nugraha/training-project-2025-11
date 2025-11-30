package com.training.apigateway.infrastructure.web;

import com.training.apigateway.application.dto.LoginRequest;
import com.training.apigateway.application.dto.LoginResponse;
import com.training.apigateway.application.usecase.AuthenticateUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController - REST controller for authentication operations.
 * Depends ONLY on application layer use cases.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;

    public AuthController(AuthenticateUserUseCase authenticateUserUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
    }

    /**
     * POST /api/v1/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authenticateUserUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/v1/auth/logout
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // In a stateless JWT setup, logout is typically handled client-side
        // by removing the token. This endpoint is a placeholder.
        return ResponseEntity.ok().build();
    }
}
