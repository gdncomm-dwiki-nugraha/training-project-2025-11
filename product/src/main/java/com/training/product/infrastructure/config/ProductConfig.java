package com.training.product.infrastructure.config;

import com.training.product.application.usecase.GetProductDetailUseCase;
import com.training.product.application.usecase.SearchProductUseCase;
import com.training.product.domain.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ProductConfig - Infrastructure configuration for Product service.
 * Wires domain and application layer components.
 */
@Configuration
public class ProductConfig {

    @Bean
    public SearchProductUseCase searchProductUseCase(ProductRepository productRepository) {
        return new SearchProductUseCase(productRepository);
    }

    @Bean
    public GetProductDetailUseCase getProductDetailUseCase(ProductRepository productRepository) {
        return new GetProductDetailUseCase(productRepository);
    }
}
