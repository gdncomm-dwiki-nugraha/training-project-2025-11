package com.gdn.training.product.infrastructure.persistence.mapper;

import java.util.UUID;

import com.gdn.training.product.domain.model.Product;
import com.gdn.training.product.infrastructure.persistence.entity.ProductEntity;

public class ProductMapper {
    public static Product toDomain(ProductEntity entity) {
        return new Product(
                UUID.fromString(entity.getProductId()),
                UUID.fromString(entity.getSellerId()),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getPrice(),
                entity.getStock(),
                Product.Status.valueOf(entity.getStatus()),
                entity.getCreatedAt());
    }

    public static ProductEntity toEntity(Product domain) {
        return new ProductEntity(
                domain.getProductId().toString(),
                domain.getSellerId().toString(),
                domain.getName(),
                domain.getDescription(),
                domain.getCategory(),
                domain.getPrice(),
                domain.getStock(),
                domain.getStatus().name(),
                domain.getCreatedAt());
    }
}
