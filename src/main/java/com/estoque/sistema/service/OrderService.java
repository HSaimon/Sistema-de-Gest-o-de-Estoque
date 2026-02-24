package com.estoque.sistema.service;

import com.estoque.sistema.dto.OrderItemRequestDTO;
import com.estoque.sistema.dto.OrderItemResponseDTO;
import com.estoque.sistema.dto.OrderRequestDTO;
import com.estoque.sistema.dto.OrderResponseDTO;
import com.estoque.sistema.exception.InsufficientStockException;
import com.estoque.sistema.exception.ResourceNotFoundException;
import com.estoque.sistema.model.*;
import com.estoque.sistema.repository.OrderRepository;
import com.estoque.sistema.repository.ProductRepository;
import com.estoque.sistema.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        Order order = Order.builder()
                .customerName(request.customerName())
                .createdAt(LocalDateTime.now())
                .total(BigDecimal.ZERO)
                .build();

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + itemRequest.productId()));

            if (product.getStockQuantity() < itemRequest.quantity()) {
                throw new InsufficientStockException("Estoque insuficiente para o produto: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - itemRequest.quantity());
            productRepository.save(product);

            StockMovement movement = StockMovement.builder()
                    .product(product)
                    .type(MovementType.SAIDA)
                    .quantity(itemRequest.quantity())
                    .createdAt(LocalDateTime.now())
                    .reason("Pedido de venda")
                    .build();
            stockMovementRepository.save(movement);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .unitPrice(product.getPrice())
                    .build();
            items.add(orderItem);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity())));
        }

        order.setItems(items);
        order.setTotal(total);
        Order saved = orderRepository.save(order);

        return toDTO(saved);
    }

    public Page<OrderResponseDTO> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(this::toDTO);
    }

    private OrderResponseDTO toDTO(Order order) {
        List<OrderItemResponseDTO> itemDTOs = order.getItems().stream()
                .map(i -> new OrderItemResponseDTO(
                        i.getProduct().getId(),
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getUnitPrice(),
                        i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity()))
                ))
                .toList();
        return new OrderResponseDTO(order.getId(), order.getCustomerName(), order.getTotal(), order.getCreatedAt(), itemDTOs);
    }
}
