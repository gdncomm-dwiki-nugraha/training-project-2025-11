package com.training.member.infrastructure.web;

import com.training.member.application.dto.LoginRequest;
import com.training.member.application.dto.MemberResponse;
import com.training.member.application.dto.RegisterRequest;
import com.training.member.application.usecase.LoginMemberUseCase;
import com.training.member.application.usecase.RegisterMemberUseCase;
import com.training.member.domain.model.Member;
import com.training.member.infrastructure.persistence.mapper.MemberMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * MemberController - REST controller for member operations.
 * Depends ONLY on application layer use cases.
 */
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final RegisterMemberUseCase registerMemberUseCase;
    private final LoginMemberUseCase loginMemberUseCase;
    private final MemberMapper memberMapper;

    public MemberController(
            RegisterMemberUseCase registerMemberUseCase,
            LoginMemberUseCase loginMemberUseCase,
            MemberMapper memberMapper) {
        this.registerMemberUseCase = registerMemberUseCase;
        this.loginMemberUseCase = loginMemberUseCase;
        this.memberMapper = memberMapper;
    }

    /**
     * POST /api/v1/members/register
     */
    @PostMapping("/register")
    public ResponseEntity<MemberResponse> register(@Valid @RequestBody RegisterRequest request) {
        MemberResponse response = registerMemberUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * POST /api/v1/members/login
     */
    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@Valid @RequestBody LoginRequest request) {
        Member member = loginMemberUseCase.execute(request);
        MemberResponse response = memberMapper.domainToResponse(member);
        return ResponseEntity.ok(response);
    }
}
