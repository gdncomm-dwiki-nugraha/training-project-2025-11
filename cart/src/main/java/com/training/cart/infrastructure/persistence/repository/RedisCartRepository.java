package com.training.cart.infrastructure.persistence.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.cart.domain.model.Cart;
import com.training.cart.domain.model.CartItem;
import com.training.cart.domain.repository.CartRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * RedisCartRepository - Adapter implementing domain CartRepository interface.
 * Uses Redis for cart persistence.
 */
@Component
public class RedisCartRepository implements CartRepository {

    private static final String KEY_PREFIX = "cart:";

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisCartRepository(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<Cart> findByUserId(UUID userId) {
        String key = KEY_PREFIX + userId.toString();
        String json = redisTemplate.opsForValue().get(key);

        if (json == null) {
            return Optional.empty();
        }

        try {
            CartDto cartDto = objectMapper.readValue(json, CartDto.class);
            return Optional.of(dtoToDomain(cartDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize cart from Redis", e);
        }
    }

    @Override
    public void save(Cart cart) {
        String key = KEY_PREFIX + cart.getUserId().toString();
        CartDto cartDto = domainToDto(cart);

        try {
            String json = objectMapper.writeValueAsString(cartDto);
            redisTemplate.opsForValue().set(key, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize cart to Redis", e);
        }
    }

    @Override
    public void deleteByUserId(UUID userId) {
        String key = KEY_PREFIX + userId.toString();
        redisTemplate.delete(key);
    }

    // Internal DTO for Redis serialization
    private static class CartDto {
        public String userId;
        public List<CartItemDto> items;

        public CartDto() {
        }

        public CartDto(String userId, List<CartItemDto> items) {
            this.userId = userId;
            this.items = items;
        }
    }

    private static class CartItemDto {
        public String productId;
        public int quantity;

        public CartItemDto() {
        }

        public CartItemDto(String productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }
    }

    private CartDto domainToDto(Cart cart) {
        List<CartItemDto> itemDtos = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            itemDtos.add(new CartItemDto(item.getProductId().toString(), item.getQuantity()));
        }
        return new CartDto(cart.getUserId().toString(), itemDtos);
    }

    private Cart dtoToDomain(CartDto dto) {
        UUID userId = UUID.fromString(dto.userId);
        Cart cart = new Cart(userId);

        for (CartItemDto itemDto : dto.items) {
            UUID productId = UUID.fromString(itemDto.productId);
            cart.addItem(productId, itemDto.quantity);
        }

        return cart;
    }
}
