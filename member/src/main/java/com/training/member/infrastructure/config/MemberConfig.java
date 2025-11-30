package com.training.member.infrastructure.config;

import com.training.member.application.usecase.LoginMemberUseCase;
import com.training.member.application.usecase.RegisterMemberUseCase;
import com.training.member.domain.repository.MemberRepository;
import com.training.member.domain.service.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MemberConfig - Infrastructure configuration for Member service.
 * Wires domain and application layer components.
 */
@Configuration
public class MemberConfig {

    @Bean
    public RegisterMemberUseCase registerMemberUseCase(
            MemberRepository memberRepository,
            PasswordHasher passwordHasher) {
        return new RegisterMemberUseCase(memberRepository, passwordHasher);
    }

    @Bean
    public LoginMemberUseCase loginMemberUseCase(
            MemberRepository memberRepository,
            PasswordHasher passwordHasher) {
        return new LoginMemberUseCase(memberRepository, passwordHasher);
    }
}
