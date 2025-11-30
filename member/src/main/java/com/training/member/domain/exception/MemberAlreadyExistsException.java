package com.training.member.domain.exception;

/**
 * Exception thrown when attempting to register a member with an email
 * that already exists in the system.
 */
public class MemberAlreadyExistsException extends RuntimeException {

    public MemberAlreadyExistsException(String email) {
        super("Member with email '" + email + "' already exists");
    }

    public MemberAlreadyExistsException(String email, Throwable cause) {
        super("Member with email '" + email + "' already exists", cause);
    }
}
