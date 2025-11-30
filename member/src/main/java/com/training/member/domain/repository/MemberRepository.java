package com.training.member.domain.repository;

import com.training.member.domain.model.Member;

import java.util.Optional;
import java.util.UUID;

/**
 * Member repository interface - Domain layer.
 * This is a port (interface) that will be implemented by infrastructure layer.
 * NO Spring annotations here.
 */
public interface MemberRepository {

    /**
     * Find a member by email address.
     *
     * @param email the email address
     * @return Optional containing the member if found
     */
    Optional<Member> findByEmail(String email);

    /**
     * Save a member (create or update).
     *
     * @param member the member to save
     * @return the saved member
     */
    Member save(Member member);

    /**
     * Find a member by ID.
     *
     * @param id the member UUID
     * @return Optional containing the member if found
     */
    Optional<Member> findById(UUID id);
}
