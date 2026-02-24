package com.estoque.sistema.service;

import com.estoque.sistema.dto.ProductRequestDTO;
import com.estoque.sistema.exception.ResourceNotFoundException;
import com.estoque.sistema.model.Product;
import com.estoque.sistema.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProduct() {
        ProductRequestDTO dto = new ProductRequestDTO("Mouse", "Mouse gamer", BigDecimal.TEN, 20, "Periféricos");
        Product saved = Product.builder().id(1L).name("Mouse").description("Mouse gamer").price(BigDecimal.TEN).stockQuantity(20).category("Periféricos").build();

        when(productRepository.save(any(Product.class))).thenReturn(saved);

        var response = productService.create(dto);

        assertEquals(1L, response.id());
        assertEquals("Mouse", response.name());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldThrowWhenProductNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.findById(99L));
    }
}
