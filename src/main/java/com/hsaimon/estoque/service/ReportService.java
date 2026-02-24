package com.hsaimon.estoque.service;

import com.hsaimon.estoque.dto.*;
import com.hsaimon.estoque.repository.OrderItemRepository;
import com.hsaimon.estoque.repository.OrderRepository;
import com.hsaimon.estoque.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @Cacheable(value = "reports", key = "'low-stock-' + #threshold")
    public List<LowStockProductDto> lowStock(int threshold) {
        return productRepository.findLowStockProducts(threshold);
    }

    @Cacheable(value = "reports", key = "'top-selling-' + #limit")
    public List<TopSellingProductDto> topSelling(int limit) {
        return orderItemRepository.findTopSelling(PageRequest.of(0, limit));
    }

    @Cacheable(value = "reports", key = "'movements-' + #limit")
    public List<StockMovementDto> movements(int limit) {
        return orderItemRepository.findStockMovements(PageRequest.of(0, limit));
    }

    @Cacheable(value = "dashboard", key = "'main'")
    public DashboardResponse dashboard() {
        return new DashboardResponse(
                orderRepository.count(),
                productRepository.count(),
                orderRepository.getTotalRevenue(),
                (long) productRepository.findLowStockProducts(10).size(),
                orderItemRepository.findTopSelling(PageRequest.of(0, 5))
        );
    }
}
