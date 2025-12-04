package com.gdn.training.product.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gdn.training.product.infrastructure.persistence.entity.ProductEntity;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, String> {
    Optional<ProductEntity> findById(String productId);
}
