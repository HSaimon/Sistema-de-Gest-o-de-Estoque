package com.estoque.sistema.service;

import com.estoque.sistema.dto.OrderItemRequestDTO;
import com.estoque.sistema.dto.OrderRequestDTO;
import com.estoque.sistema.exception.InsufficientStockException;
import com.estoque.sistema.model.Product;
import com.estoque.sistema.repository.OrderRepository;
import com.estoque.sistema.repository.ProductRepository;
import com.estoque.sistema.repository.StockMovementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private StockMovementRepository stockMovementRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldThrowWhenStockIsInsufficient() {
        Product keyboard = Product.builder()
                .id(1L)
                .name("Teclado")
                .price(BigDecimal.valueOf(100))
                .stockQuantity(1)
                .build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(keyboard));

        OrderRequestDTO request = new OrderRequestDTO("Cliente", List.of(new OrderItemRequestDTO(1L, 2)));

        assertThrows(InsufficientStockException.class, () -> orderService.createOrder(request));
    }
}
