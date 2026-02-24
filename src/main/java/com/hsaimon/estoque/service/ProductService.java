package com.hsaimon.estoque.service;

import com.hsaimon.estoque.dto.ProductRequest;
import com.hsaimon.estoque.dto.ProductResponse;
import com.hsaimon.estoque.exception.ResourceNotFoundException;
import com.hsaimon.estoque.model.Product;
import com.hsaimon.estoque.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @CacheEvict(value = {"dashboard", "reports"}, allEntries = true)
    public ProductResponse create(ProductRequest request) {
        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .category(request.category())
                .build();
        return toResponse(productRepository.save(product));
    }

    @Cacheable(value = "products", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #category")
    public Page<ProductResponse> list(Pageable pageable, String category) {
        if (category != null && !category.isBlank()) {
            return productRepository.findAllByCategoryIgnoreCase(category, pageable).map(this::toResponse);
        }
        return productRepository.findAll(pageable).map(this::toResponse);
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));
    }

    ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory());
    }
}
