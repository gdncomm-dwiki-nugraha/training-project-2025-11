package com.training.cart.domain.repository;

import com.training.cart.domain.model.Cart;

import java.util.Optional;
import java.util.UUID;

/**
 * Cart repository interface - Domain layer.
 * This is a port (interface) that will be implemented by infrastructure layer.
 * NO Spring annotations here.
 */
public interface CartRepository {

    /**
     * Find a cart by user ID.
     *
     * @param userId the user UUID
     * @return Optional containing the cart if found
     */
    Optional<Cart> findByUserId(UUID userId);

    /**
     * Save a cart (create or update).
     *
     * @param cart the cart to save
     */
    void save(Cart cart);

    /**
     * Delete a cart by user ID.
     *
     * @param userId the user UUID
     */
    void deleteByUserId(UUID userId);
}
