package com.training.product.domain.repository;

import com.training.product.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Product repository interface - Domain layer.
 * This is a port (interface) that will be implemented by infrastructure layer.
 * NO Spring annotations here.
 */
public interface ProductRepository {

    /**
     * Find a product by its ID.
     *
     * @param id the product UUID
     * @return Optional containing the product if found
     */
    Optional<Product> findById(UUID id);

    /**
     * Search products by keyword with pagination.
     *
     * @param keyword the search keyword (searches name, description, category)
     * @param page    the page number (0-indexed)
     * @param size    the page size
     * @return list of products matching the search criteria
     */
    List<Product> search(String keyword, int page, int size);

    /**
     * Save a product (create or update).
     *
     * @param product the product to save
     * @return the saved product
     */
    Product save(Product product);

    /**
     * Find all products with pagination.
     *
     * @param page the page number (0-indexed)
     * @param size the page size
     * @return list of products
     */
    List<Product> findAll(int page, int size);
}
