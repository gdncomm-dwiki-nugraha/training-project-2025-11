package com.training.cart.infrastructure.persistence.mapper;

import com.training.cart.application.dto.CartItemResponse;
import com.training.cart.application.dto.CartResponse;
import com.training.cart.domain.model.Cart;
import com.training.cart.domain.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CartMapper - Maps between domain models and DTOs.
 */
@Component
public class CartMapper {

    /**
     * Convert Cart domain model to CartResponse DTO.
     */
    public CartResponse domainToResponse(Cart cart) {
        if (cart == null) {
            return null;
        }

        List<CartItemResponse> itemResponses = cart.getItems().stream()
                .map(this::itemToResponse)
                .collect(Collectors.toList());

        return new CartResponse(cart.getUserId(), itemResponses);
    }

    /**
     * Convert CartItem to CartItemResponse DTO.
     */
    public CartItemResponse itemToResponse(CartItem item) {
        if (item == null) {
            return null;
        }
        return new CartItemResponse(item.getProductId(), item.getQuantity());
    }
}
