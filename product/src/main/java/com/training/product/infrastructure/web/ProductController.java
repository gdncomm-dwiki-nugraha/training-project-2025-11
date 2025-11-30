package com.training.product.infrastructure.web;

import com.training.product.application.dto.ProductResponse;
import com.training.product.application.usecase.GetProductDetailUseCase;
import com.training.product.application.usecase.SearchProductUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * ProductController - REST controller for product operations.
 * Depends ONLY on application layer use cases.
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final SearchProductUseCase searchProductUseCase;
    private final GetProductDetailUseCase getProductDetailUseCase;

    public ProductController(
            SearchProductUseCase searchProductUseCase,
            GetProductDetailUseCase getProductDetailUseCase) {
        this.searchProductUseCase = searchProductUseCase;
        this.getProductDetailUseCase = getProductDetailUseCase;
    }

    /**
     * GET /api/v1/products?keyword=...&page=0&size=20
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        List<ProductResponse> products = searchProductUseCase.execute(keyword, page, size);
        return ResponseEntity.ok(products);
    }

    /**
     * GET /api/v1/products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductDetail(@PathVariable UUID id) {
        ProductResponse product = getProductDetailUseCase.execute(id);
        return ResponseEntity.ok(product);
    }
}
