package com.hsaimon.estoque.service;

import com.hsaimon.estoque.dto.*;
import com.hsaimon.estoque.exception.BusinessException;
import com.hsaimon.estoque.model.OrderEntity;
import com.hsaimon.estoque.model.OrderItem;
import com.hsaimon.estoque.model.Product;
import com.hsaimon.estoque.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Transactional
    @CacheEvict(value = {"dashboard", "reports", "products"}, allEntries = true)
    public OrderResponse create(OrderRequest request) {
        OrderEntity order = OrderEntity.builder()
                .createdAt(LocalDateTime.now())
                .total(BigDecimal.ZERO)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.items()) {
            Product product = productService.findById(itemRequest.productId());
            if (product.getStockQuantity() < itemRequest.quantity()) {
                throw new BusinessException("Estoque insuficiente para o produto: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - itemRequest.quantity());

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .unitPrice(product.getPrice())
                    .build();

            orderItems.add(item);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity())));
        }

        order.setItems(orderItems);
        order.setTotal(total);
        OrderEntity saved = orderRepository.save(order);

        List<OrderItemResponse> itemResponses = saved.getItems().stream()
                .map(i -> new OrderItemResponse(
                        i.getProduct().getId(),
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getUnitPrice(),
                        i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity()))
                )).toList();

        return new OrderResponse(saved.getId(), saved.getCreatedAt(), saved.getTotal(), itemResponses);
    }
}
