package com.training.cart.infrastructure.web;

import com.training.cart.application.dto.AddCartItemRequest;
import com.training.cart.application.dto.CartResponse;
import com.training.cart.application.usecase.AddItemToCartUseCase;
import com.training.cart.application.usecase.GetCartUseCase;
import com.training.cart.application.usecase.RemoveItemFromCartUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * CartController - REST controller for cart operations.
 * Depends ONLY on application layer use cases.
 */
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final AddItemToCartUseCase addItemToCartUseCase;
    private final RemoveItemFromCartUseCase removeItemFromCartUseCase;
    private final GetCartUseCase getCartUseCase;

    public CartController(
            AddItemToCartUseCase addItemToCartUseCase,
            RemoveItemFromCartUseCase removeItemFromCartUseCase,
            GetCartUseCase getCartUseCase) {
        this.addItemToCartUseCase = addItemToCartUseCase;
        this.removeItemFromCartUseCase = removeItemFromCartUseCase;
        this.getCartUseCase = getCartUseCase;
    }

    /**
     * GET /api/v1/cart?userId=...
     */
    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestParam UUID userId) {
        CartResponse cart = getCartUseCase.execute(userId);
        return ResponseEntity.ok(cart);
    }

    /**
     * POST /api/v1/cart?userId=...
     */
    @PostMapping
    public ResponseEntity<CartResponse> addItem(
            @RequestParam UUID userId,
            @Valid @RequestBody AddCartItemRequest request) {

        CartResponse cart = addItemToCartUseCase.execute(userId, request);
        return ResponseEntity.ok(cart);
    }

    /**
     * DELETE /api/v1/cart/{productId}?userId=...
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<CartResponse> removeItem(
            @PathVariable UUID productId,
            @RequestParam UUID userId) {

        CartResponse cart = removeItemFromCartUseCase.execute(userId, productId);
        return ResponseEntity.ok(cart);
    }
}
