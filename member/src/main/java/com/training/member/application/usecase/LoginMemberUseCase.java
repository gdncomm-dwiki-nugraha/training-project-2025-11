package com.training.member.application.usecase;

import com.training.member.application.dto.LoginRequest;
import com.training.member.domain.exception.InvalidCredentialException;
import com.training.member.domain.model.Member;
import com.training.member.domain.repository.MemberRepository;
import com.training.member.domain.service.PasswordHasher;

import java.util.Optional;

/**
 * LoginMemberUseCase - Application service for member login/authentication.
 * Pure Java class with NO Spring annotations.
 * Uses constructor injection for dependencies.
 */
public class LoginMemberUseCase {

    private final MemberRepository memberRepository;
    private final PasswordHasher passwordHasher;

    public LoginMemberUseCase(MemberRepository memberRepository, PasswordHasher passwordHasher) {
        this.memberRepository = memberRepository;
        this.passwordHasher = passwordHasher;
    }

    /**
     * Execute member login.
     *
     * @param request the login request
     * @return Member domain object if credentials are valid
     * @throws InvalidCredentialException if email or password is invalid
     */
    public Member execute(LoginRequest request) {
        // Find member by email
        Optional<Member> memberOptional = memberRepository.findByEmail(request.email());

        if (memberOptional.isEmpty()) {
            throw new InvalidCredentialException();
        }

        Member member = memberOptional.get();

        // Verify password
        boolean passwordMatches = passwordHasher.matches(request.password(), member.getPasswordHash());

        if (!passwordMatches) {
            throw new InvalidCredentialException();
        }

        // Return member if authentication successful
        return member;
    }
}
