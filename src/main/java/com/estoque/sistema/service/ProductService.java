package com.estoque.sistema.service;

import com.estoque.sistema.dto.ProductRequestDTO;
import com.estoque.sistema.dto.ProductResponseDTO;
import com.estoque.sistema.exception.ResourceNotFoundException;
import com.estoque.sistema.model.Product;
import com.estoque.sistema.repository.ProductRepository;
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

    @Cacheable(value = "products", key = "#id")
    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));
        return toDTO(product);
    }

    public Page<ProductResponseDTO> findAll(String name, Pageable pageable) {
        Page<Product> page = (name == null || name.isBlank())
                ? productRepository.findAll(pageable)
                : productRepository.findByNameContainingIgnoreCase(name, pageable);
        return page.map(this::toDTO);
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product saved = productRepository.save(Product.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .stockQuantity(dto.stockQuantity())
                .category(dto.category())
                .build());
        return toDTO(saved);
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());
        product.setCategory(dto.category());

        return toDTO(productRepository.save(product));
    }

    @CacheEvict(value = "products", allEntries = true)
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductResponseDTO toDTO(Product p) {
        return new ProductResponseDTO(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getStockQuantity(), p.getCategory());
    }
}
