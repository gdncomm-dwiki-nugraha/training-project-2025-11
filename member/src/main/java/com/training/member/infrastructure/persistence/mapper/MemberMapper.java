package com.training.member.infrastructure.persistence.mapper;

import com.training.member.application.dto.MemberResponse;
import com.training.member.domain.model.Member;
import com.training.member.infrastructure.persistence.entity.MemberEntity;
import org.springframework.stereotype.Component;

/**
 * MemberMapper - Maps between domain models, entities, and DTOs.
 */
@Component
public class MemberMapper {

    /**
     * Convert MemberEntity to Member domain model.
     */
    public Member entityToDomain(MemberEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Member(
                entity.getId(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getCreatedAt());
    }

    /**
     * Convert Member domain model to MemberEntity.
     */
    public MemberEntity domainToEntity(Member member) {
        if (member == null) {
            return null;
        }
        return new MemberEntity(
                member.getId(),
                member.getEmail(),
                member.getPasswordHash(),
                member.getCreatedAt());
    }

    /**
     * Convert Member domain model to MemberResponse DTO.
     */
    public MemberResponse domainToResponse(Member member) {
        if (member == null) {
            return null;
        }
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getCreatedAt());
    }
}
