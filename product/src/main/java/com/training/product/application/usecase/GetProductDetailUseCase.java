package com.training.product.application.usecase;

import com.training.product.application.dto.ProductResponse;
import com.training.product.domain.exception.ProductNotFoundException;
import com.training.product.domain.model.Product;
import com.training.product.domain.repository.ProductRepository;

import java.util.UUID;

/**
 * GetProductDetailUseCase - Application service for retrieving product details.
 * Pure Java class with NO Spring annotations.
 * Uses constructor injection for dependencies.
 */
public class GetProductDetailUseCase {

    private final ProductRepository productRepository;

    public GetProductDetailUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Execute get product detail.
     *
     * @param productId the product UUID
     * @return ProductResponse with product data
     * @throws ProductNotFoundException if product is not found
     */
    public ProductResponse execute(UUID productId) {
        // Find product by ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        // Map to DTO and return
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory());
    }
}
