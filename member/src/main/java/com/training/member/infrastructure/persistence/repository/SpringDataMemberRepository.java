package com.training.member.infrastructure.persistence.repository;

import com.training.member.infrastructure.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for MemberEntity.
 */
@Repository
public interface SpringDataMemberRepository extends JpaRepository<MemberEntity, UUID> {

    /**
     * Find member by email.
     */
    Optional<MemberEntity> findByEmail(String email);
}
