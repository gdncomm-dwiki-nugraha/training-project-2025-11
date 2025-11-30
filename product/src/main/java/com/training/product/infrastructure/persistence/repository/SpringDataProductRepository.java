package com.training.product.infrastructure.persistence.repository;

import com.training.product.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for ProductEntity.
 */
@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, UUID> {

    /**
     * Search products by keyword (case-insensitive).
     * Searches in name, description, and category fields.
     */
    @Query("SELECT p FROM ProductEntity p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ProductEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
