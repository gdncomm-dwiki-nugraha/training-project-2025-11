package com.training.cart.domain.model;

import com.training.cart.domain.exception.CartItemNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Cart - Aggregate Root representing a shopping cart.
 * This is a pure Java class with NO Spring annotations.
 * Implements the Aggregate Pattern with business logic encapsulated.
 */
public class Cart {

    private UUID userId;
    private List<CartItem> items;

    public Cart(UUID userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public Cart(UUID userId, List<CartItem> items) {
        this.userId = userId;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
    }

    public Cart() {
        this.items = new ArrayList<>();
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    /**
     * Get an unmodifiable view of the cart items.
     * Prevents external modification of the internal list.
     *
     * @return unmodifiable list of cart items
     */
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(List<CartItem> items) {
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
    }

    /**
     * Add an item to the cart.
     * If the product already exists, increase its quantity.
     * If it's a new product, add it to the cart.
     *
     * @param productId the product UUID
     * @param quantity  the quantity to add
     */
    public void addItem(UUID productId, int quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        CartItem existingItem = findItemByProductId(productId);

        if (existingItem != null) {
            // Product already in cart, increase quantity
            existingItem.increaseQuantity(quantity);
        } else {
            // New product, add to cart
            items.add(new CartItem(productId, quantity));
        }
    }

    /**
     * Remove an item from the cart by product ID.
     *
     * @param productId the product UUID to remove
     * @throws CartItemNotFoundException if the item is not in the cart
     */
    public void removeItem(UUID productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        CartItem itemToRemove = findItemByProductId(productId);

        if (itemToRemove == null) {
            throw new CartItemNotFoundException(productId);
        }

        items.remove(itemToRemove);
    }

    /**
     * Update the quantity of a specific item in the cart.
     *
     * @param productId   the product UUID
     * @param newQuantity the new quantity
     * @throws CartItemNotFoundException if the item is not in the cart
     */
    public void updateItemQuantity(UUID productId, int newQuantity) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        CartItem item = findItemByProductId(productId);

        if (item == null) {
            throw new CartItemNotFoundException(productId);
        }

        item.setQuantity(newQuantity);
    }

    /**
     * Clear all items from the cart.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Get the total number of items in the cart.
     *
     * @return total item count
     */
    public int getTotalItems() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    /**
     * Check if the cart is empty.
     *
     * @return true if cart has no items, false otherwise
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Find a cart item by product ID.
     *
     * @param productId the product UUID
     * @return the CartItem if found, null otherwise
     */
    private CartItem findItemByProductId(UUID productId) {
        return items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cart cart = (Cart) o;
        return Objects.equals(userId, cart.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId=" + userId +
                ", items=" + items +
                '}';
    }
}
