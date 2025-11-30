package com.training.cart.application.usecase;

import com.training.cart.application.dto.CartItemResponse;
import com.training.cart.application.dto.CartResponse;
import com.training.cart.domain.model.Cart;
import com.training.cart.domain.repository.CartRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * GetCartUseCase - Application service for retrieving cart data.
 * Pure Java class with NO Spring annotations.
 * Uses constructor injection for dependencies.
 */
public class GetCartUseCase {

    private final CartRepository cartRepository;

    public GetCartUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Execute get cart.
     *
     * @param userId the user UUID
     * @return CartResponse with cart data (empty cart if not found)
     */
    public CartResponse execute(UUID userId) {
        // Load cart or return empty cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(userId));

        // Map to DTO and return
        return mapToCartResponse(cart);
    }

    private CartResponse mapToCartResponse(Cart cart) {
        List<CartItemResponse> itemResponses = cart.getItems().stream()
                .map(item -> new CartItemResponse(item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());

        return new CartResponse(cart.getUserId(), itemResponses);
    }
}
