package com.training.apigateway.infrastructure.security.jwt;

import com.training.apigateway.domain.service.JwtProvider;
import com.training.apigateway.domain.service.JwtProviderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JwtProviderFactoryImpl - Factory implementation for creating JwtProvider
 * instances.
 * Implements the Factory Pattern.
 */
@Component
public class JwtProviderFactoryImpl implements JwtProviderFactory {

    private final String jwtSecret;
    private final long jwtExpirationHours;

    public JwtProviderFactoryImpl(
            @Value("${jwt.secret:my-secret-key-for-jwt-signing-minimum-256-bits}") String jwtSecret,
            @Value("${jwt.expiration-hours:24}") long jwtExpirationHours) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationHours = jwtExpirationHours;
    }

    @Override
    public JwtProvider createProvider(String type) {
        return switch (type.toUpperCase()) {
            case "HMAC", "HS256" -> new HmacJwtProvider(jwtSecret, jwtExpirationHours);
            case "RSA", "RS256" -> throw new UnsupportedOperationException("RSA JWT provider not yet implemented");
            default -> throw new IllegalArgumentException("Unknown JWT provider type: " + type);
        };
    }
}
