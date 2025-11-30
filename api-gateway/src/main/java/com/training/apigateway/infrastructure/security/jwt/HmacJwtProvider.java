package com.training.apigateway.infrastructure.security.jwt;

import com.training.apigateway.domain.exception.InvalidJwtException;
import com.training.apigateway.domain.model.AuthUser;
import com.training.apigateway.domain.service.JwtProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * HmacJwtProvider - JWT provider using HMAC-SHA256 algorithm.
 * Implements domain JwtProvider interface.
 */
public class HmacJwtProvider implements JwtProvider {

    private final SecretKey secretKey;
    private final long expirationHours;

    public HmacJwtProvider(String secret, long expirationHours) {
        // Ensure secret is at least 256 bits (32 bytes) for HS256
        String paddedSecret = secret.length() >= 32 ? secret : String.format("%-32s", secret).replace(' ', '0');
        this.secretKey = Keys.hmacShaKeyFor(paddedSecret.getBytes(StandardCharsets.UTF_8));
        this.expirationHours = expirationHours;
    }

    @Override
    public String generate(AuthUser user) {
        Instant now = Instant.now();
        Instant expiration = now.plus(expirationHours, ChronoUnit.HOURS);

        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public AuthUser validate(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userIdStr = claims.getSubject();
            UUID userId = UUID.fromString(userIdStr);

            return new AuthUser(userId);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtException("Invalid or expired JWT token", e);
        }
    }
}
