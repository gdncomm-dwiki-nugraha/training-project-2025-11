package com.training.product.infrastructure.persistence.repository;

import com.training.product.domain.model.Product;
import com.training.product.domain.repository.ProductRepository;
import com.training.product.infrastructure.persistence.entity.ProductEntity;
import com.training.product.infrastructure.persistence.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * JpaProductRepository - Adapter implementing domain ProductRepository
 * interface.
 * Uses Spring Data JPA under the hood.
 */
@Component
public class JpaProductRepository implements ProductRepository {

    private final SpringDataProductRepository springDataRepository;
    private final ProductMapper mapper;

    public JpaProductRepository(SpringDataProductRepository springDataRepository, ProductMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return springDataRepository.findById(id)
                .map(mapper::entityToDomain);
    }

    @Override
    public List<Product> search(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductEntity> entityPage = springDataRepository.searchByKeyword(keyword, pageRequest);

        return entityPage.getContent().stream()
                .map(mapper::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = mapper.domainToEntity(product);
        ProductEntity savedEntity = springDataRepository.save(entity);
        return mapper.entityToDomain(savedEntity);
    }

    @Override
    public List<Product> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductEntity> entityPage = springDataRepository.findAll(pageRequest);

        return entityPage.getContent().stream()
                .map(mapper::entityToDomain)
                .collect(Collectors.toList());
    }
}
