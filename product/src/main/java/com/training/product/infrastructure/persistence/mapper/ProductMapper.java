package com.training.product.infrastructure.persistence.mapper;

import com.training.product.application.dto.ProductResponse;
import com.training.product.domain.model.Product;
import com.training.product.infrastructure.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

/**
 * ProductMapper - Maps between domain models, entities, and DTOs.
 */
@Component
public class ProductMapper {

    /**
     * Convert ProductEntity to Product domain model.
     */
    public Product entityToDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getCategory());
    }

    /**
     * Convert Product domain model to ProductEntity.
     */
    public ProductEntity domainToEntity(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory());
    }

    /**
     * Convert Product domain model to ProductResponse DTO.
     */
    public ProductResponse domainToResponse(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory());
    }
}
