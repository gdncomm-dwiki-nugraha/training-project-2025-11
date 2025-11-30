package com.training.member.application.usecase;

import com.training.member.application.dto.MemberResponse;
import com.training.member.application.dto.RegisterRequest;
import com.training.member.domain.exception.MemberAlreadyExistsException;
import com.training.member.domain.model.Member;
import com.training.member.domain.repository.MemberRepository;
import com.training.member.domain.service.PasswordHasher;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * RegisterMemberUseCase - Application service for member registration.
 * Pure Java class with NO Spring annotations.
 * Uses constructor injection for dependencies.
 */
public class RegisterMemberUseCase {

    private final MemberRepository memberRepository;
    private final PasswordHasher passwordHasher;

    public RegisterMemberUseCase(MemberRepository memberRepository, PasswordHasher passwordHasher) {
        this.memberRepository = memberRepository;
        this.passwordHasher = passwordHasher;
    }

    /**
     * Execute member registration.
     *
     * @param request the registration request
     * @return MemberResponse with created member data
     * @throws MemberAlreadyExistsException if email already exists
     */
    public MemberResponse execute(RegisterRequest request) {
        // Check if email already exists
        Optional<Member> existingMember = memberRepository.findByEmail(request.email());
        if (existingMember.isPresent()) {
            throw new MemberAlreadyExistsException(request.email());
        }

        // Hash the password
        String passwordHash = passwordHasher.hash(request.password());

        // Create new member
        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setEmail(request.email());
        member.setPasswordHash(passwordHash);
        member.setCreatedAt(LocalDateTime.now());

        // Save member
        Member savedMember = memberRepository.save(member);

        // Map to DTO and return
        return new MemberResponse(
                savedMember.getId(),
                savedMember.getEmail(),
                savedMember.getCreatedAt());
    }
}
