package com.training.apigateway.infrastructure.config;

import com.training.apigateway.application.port.MemberAuthPort;
import com.training.apigateway.application.usecase.AuthenticateUserUseCase;
import com.training.apigateway.domain.service.JwtProvider;
import com.training.apigateway.domain.service.JwtProviderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * GatewayConfig - Infrastructure configuration for API Gateway.
 * Wires domain and application layer components.
 */
@Configuration
public class GatewayConfig {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public JwtProvider jwtProvider(
            JwtProviderFactory factory,
            @Value("${jwt.provider.type:HMAC}") String providerType) {
        return factory.createProvider(providerType);
    }

    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(
            MemberAuthPort memberAuthPort,
            JwtProvider jwtProvider) {
        return new AuthenticateUserUseCase(memberAuthPort, jwtProvider);
    }
}
