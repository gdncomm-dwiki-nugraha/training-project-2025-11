package com.training.product.application.usecase;

import com.training.product.application.dto.ProductResponse;
import com.training.product.domain.model.Product;
import com.training.product.domain.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SearchProductUseCase - Application service for searching products.
 * Pure Java class with NO Spring annotations.
 * Uses constructor injection for dependencies.
 */
public class SearchProductUseCase {

    private final ProductRepository productRepository;

    public SearchProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Execute product search.
     *
     * @param keyword the search keyword (can be empty or null for all products)
     * @param page    the page number (0-indexed)
     * @param size    the page size
     * @return List of ProductResponse objects
     */
    public List<ProductResponse> execute(String keyword, int page, int size) {
        // Search products
        List<Product> products;

        if (keyword == null || keyword.trim().isEmpty()) {
            // If no keyword, return all products with pagination
            products = productRepository.findAll(page, size);
        } else {
            // Search by keyword
            products = productRepository.search(keyword, page, size);
        }

        // Map to DTOs and return
        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCategory()))
                .collect(Collectors.toList());
    }
}
