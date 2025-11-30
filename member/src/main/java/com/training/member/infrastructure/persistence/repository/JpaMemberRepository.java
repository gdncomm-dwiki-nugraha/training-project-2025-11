package com.training.member.infrastructure.persistence.repository;

import com.training.member.domain.model.Member;
import com.training.member.domain.repository.MemberRepository;
import com.training.member.infrastructure.persistence.entity.MemberEntity;
import com.training.member.infrastructure.persistence.mapper.MemberMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * JpaMemberRepository - Adapter implementing domain MemberRepository interface.
 * Uses Spring Data JPA under the hood.
 */
@Component
public class JpaMemberRepository implements MemberRepository {

    private final SpringDataMemberRepository springDataRepository;
    private final MemberMapper mapper;

    public JpaMemberRepository(SpringDataMemberRepository springDataRepository, MemberMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return springDataRepository.findByEmail(email)
                .map(mapper::entityToDomain);
    }

    @Override
    public Member save(Member member) {
        MemberEntity entity = mapper.domainToEntity(member);
        MemberEntity savedEntity = springDataRepository.save(entity);
        return mapper.entityToDomain(savedEntity);
    }

    @Override
    public Optional<Member> findById(UUID id) {
        return springDataRepository.findById(id)
                .map(mapper::entityToDomain);
    }
}
