package com.training.cart.application.usecase;

import com.training.cart.application.dto.AddCartItemRequest;
import com.training.cart.application.dto.CartItemResponse;
import com.training.cart.application.dto.CartResponse;
import com.training.cart.domain.model.Cart;
import com.training.cart.domain.model.CartItem;
import com.training.cart.domain.repository.CartRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * AddItemToCartUseCase - Application service for adding items to cart.
 * Pure Java class with NO Spring annotations.
 * Uses constructor injection for dependencies.
 */
public class AddItemToCartUseCase {

    private final CartRepository cartRepository;

    public AddItemToCartUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Execute add item to cart.
     *
     * @param userId  the user UUID
     * @param request the add item request
     * @return CartResponse with updated cart data
     */
    public CartResponse execute(UUID userId, AddCartItemRequest request) {
        // Load existing cart or create new one
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(userId));

        // Add item to cart (using aggregate business logic)
        cart.addItem(request.productId(), request.quantity());

        // Save cart
        cartRepository.save(cart);

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
