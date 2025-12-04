package com.gdn.training.product.integration;

import com.gdn.training.product.domain.model.Product;
import com.gdn.training.product.domain.repository.ProductRepository;
import com.gdn.training.product.infrastructure.persistence.entity.ProductEntity;
import com.gdn.training.product.infrastructure.persistence.jpa.ProductJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for ProductRepositoryImpl using Testcontainers PostgreSQL.
 * Tests actual database interactions with real PostgreSQL instance.
 */
@SpringBootTest
@Testcontainers
public class ProductRepositoryImplIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @BeforeEach
    void setUp() {
        // Clean up database before each test
        productJpaRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFindProductById() {
        // Arrange
        String productId = UUID.randomUUID().toString();
        String sellerId = UUID.randomUUID().toString();
        ProductEntity entity = new ProductEntity();
        entity.setProductId(productId);
        entity.setSellerId(sellerId);
        entity.setName("Integration Test Product");
        entity.setDescription("Test Description");
        entity.setCategory("test-category");
        entity.setPrice(1000.0);
        entity.setStock(50);
        entity.setStatus("ACTIVE");
        entity.setCreatedAt(Instant.now());

        productJpaRepository.save(entity);

        // Act
        Optional<Product> result = productRepository.findById(productId);

        // Assert
        assertTrue(result.isPresent());
        Product product = result.get();
        assertEquals(productId, product.getProductId().toString());
        assertEquals("Integration Test Product", product.getName());
        assertEquals("test-category", product.getCategory());
        assertEquals(1000.0, product.getPrice());
        assertEquals(50, product.getStock());
        assertEquals(Product.Status.ACTIVE, product.getStatus());
    }

    @Test
    void shouldReturnEmpty_WhenProductNotFound() {
        // Arrange
        String nonExistentId = UUID.randomUUID().toString();

        // Act
        Optional<Product> result = productRepository.findById(nonExistentId);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldHandleMultipleProducts() {
        // Arrange
        ProductEntity product1 = createTestEntity("Product 1", "cat1", 100.0, 10);
        ProductEntity product2 = createTestEntity("Product 2", "cat2", 200.0, 20);
        ProductEntity product3 = createTestEntity("Product 3", "cat1", 300.0, 30);

        productJpaRepository.save(product1);
        productJpaRepository.save(product2);
        productJpaRepository.save(product3);

        // Act
        Optional<Product> result1 = productRepository.findById(product1.getProductId());
        Optional<Product> result2 = productRepository.findById(product2.getProductId());
        Optional<Product> result3 = productRepository.findById(product3.getProductId());

        // Assert
        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertTrue(result3.isPresent());
        assertEquals("Product 1", result1.get().getName());
        assertEquals("Product 2", result2.get().getName());
        assertEquals("Product 3", result3.get().getName());
    }

    @Test
    void shouldMapAllFieldsCorrectly() {
        // Arrange
        String productId = UUID.randomUUID().toString();
        String sellerId = UUID.randomUUID().toString();
        Instant createdAt = Instant.now();

        ProductEntity entity = new ProductEntity();
        entity.setProductId(productId);
        entity.setSellerId(sellerId);
        entity.setName("Complete Product");
        entity.setDescription("Full Description");
        entity.setCategory("complete-cat");
        entity.setPrice(9999.99);
        entity.setStock(100);
        entity.setStatus("INACTIVE");
        entity.setCreatedAt(createdAt);

        productJpaRepository.save(entity);

        // Act
        Optional<Product> result = productRepository.findById(productId);

        // Assert
        assertTrue(result.isPresent());
        Product product = result.get();
        assertEquals(productId, product.getProductId().toString());
        assertEquals(sellerId, product.getSellerId().toString());
        assertEquals("Complete Product", product.getName());
        assertEquals("Full Description", product.getDescription());
        assertEquals("complete-cat", product.getCategory());
        assertEquals(9999.99, product.getPrice(), 0.001);
        assertEquals(100, product.getStock());
        assertEquals(Product.Status.INACTIVE, product.getStatus());
        assertNotNull(product.getCreatedAt());
    }

    private ProductEntity createTestEntity(String name, String category, double price, int stock) {
        ProductEntity entity = new ProductEntity();
        entity.setProductId(UUID.randomUUID().toString());
        entity.setSellerId(UUID.randomUUID().toString());
        entity.setName(name);
        entity.setDescription("Test description for " + name);
        entity.setCategory(category);
        entity.setPrice(price);
        entity.setStock(stock);
        entity.setStatus("ACTIVE");
        entity.setCreatedAt(Instant.now());
        return entity;
    }
}
