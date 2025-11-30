package com.training.apigateway.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * AuthUser - Domain model representing an authenticated user.
 * This is a pure Java class with NO Spring annotations.
 */
public class AuthUser {

    private UUID userId;

    public AuthUser(UUID userId) {
        this.userId = userId;
    }

    public AuthUser() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AuthUser authUser = (AuthUser) o;
        return Objects.equals(userId, authUser.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "userId=" + userId +
                '}';
    }
}
