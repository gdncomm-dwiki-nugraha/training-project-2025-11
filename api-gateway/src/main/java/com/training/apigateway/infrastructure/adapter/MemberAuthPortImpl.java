package com.training.apigateway.infrastructure.adapter;

import com.training.apigateway.application.port.MemberAuthPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.UUID;

/**
 * MemberAuthPortImpl - Adapter for communicating with Member service.
 * Implements application layer port using RestClient.
 */
@Component
public class MemberAuthPortImpl implements MemberAuthPort {

    private final RestClient restClient;
    private final String memberServiceUrl;

    public MemberAuthPortImpl(
            RestClient.Builder restClientBuilder,
            @Value("${services.member.url:http://localhost:8081}") String memberServiceUrl) {
        this.restClient = restClientBuilder.build();
        this.memberServiceUrl = memberServiceUrl;
    }

    @Override
    public UUID verifyCredentials(String email, String password) {
        try {
            Map<String, String> request = Map.of(
                    "email", email,
                    "password", password);

            Map<String, Object> response = restClient.post()
                    .uri(memberServiceUrl + "/api/v1/members/login")
                    .body(request)
                    .retrieve()
                    .body(Map.class);

            if (response != null && response.containsKey("id")) {
                String idStr = response.get("id").toString();
                return UUID.fromString(idStr);
            }

            throw new RuntimeException("Invalid credentials");
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify credentials with member service", e);
        }
    }
}
