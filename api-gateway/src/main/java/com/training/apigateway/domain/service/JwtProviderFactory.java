package com.training.apigateway.domain.service;

/**
 * JwtProviderFactory - Factory Pattern interface.
 * Creates different implementations of JwtProvider based on type.
 * Pure Java interface with NO Spring annotations.
 */
public interface JwtProviderFactory {

    /**
     * Create a JwtProvider based on the specified type.
     *
     * @param type the type of JWT provider (e.g., "HS256", "RS256")
     * @return the appropriate JwtProvider implementation
     */
    JwtProvider createProvider(String type);
}
