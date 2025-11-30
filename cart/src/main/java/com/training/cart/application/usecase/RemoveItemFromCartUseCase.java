package com.training.cart.application.usecase;

import com.training.cart.application.dto.CartItemResponse;
import com.training.cart.application.dto.CartResponse;
import com.training.cart.domain.exception.CartNotFoundException;
import com.training.cart.domain.model.Cart;
import com.training.cart.domain.repository.CartRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * RemoveItemFromCartUseCase - Application service for removing items from cart.
 * Pure Java class with NO Spring annotations.
 * Uses constructor injection for dependencies.
 */
public class RemoveItemFromCartUseCase {

    private final CartRepository cartRepository;

    public RemoveItemFromCartUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Execute remove item from cart.
     *
     * @param userId    the user UUID
     * @param productId the product UUID to remove
     * @return CartResponse with updated cart data
     * @throws CartNotFoundException if cart is not found
     */
    public CartResponse execute(UUID userId, UUID productId) {
        // Load cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException(userId));

        // Remove item from cart (using aggregate business logic)
        cart.removeItem(productId);

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
