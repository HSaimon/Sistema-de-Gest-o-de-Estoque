package com.hsaimon.estoque.service;

import com.hsaimon.estoque.dto.OrderItemRequest;
import com.hsaimon.estoque.dto.OrderRequest;
import com.hsaimon.estoque.exception.BusinessException;
import com.hsaimon.estoque.model.OrderEntity;
import com.hsaimon.estoque.model.Product;
import com.hsaimon.estoque.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    private Product product;

    @BeforeEach
    void setup() {
        product = Product.builder()
                .id(1L)
                .name("Teclado")
                .price(BigDecimal.valueOf(100))
                .stockQuantity(10)
                .category("Periféricos")
                .build();
    }

    @Test
    void shouldCreateOrderAndReduceStock() {
        when(productService.findById(1L)).thenReturn(product);
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        var response = orderService.create(new OrderRequest(List.of(new OrderItemRequest(1L, 2))));

        assertEquals(BigDecimal.valueOf(200), response.total());
        assertEquals(8, product.getStockQuantity());
    }

    @Test
    void shouldThrowBusinessExceptionWhenStockIsInsufficient() {
        when(productService.findById(1L)).thenReturn(product);

        assertThrows(BusinessException.class,
                () -> orderService.create(new OrderRequest(List.of(new OrderItemRequest(1L, 100)))));
    }
}
