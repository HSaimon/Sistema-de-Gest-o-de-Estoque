package com.estoque.sistema.service;

import com.estoque.sistema.dto.*;
import com.estoque.sistema.repository.OrderItemRepository;
import com.estoque.sistema.repository.OrderRepository;
import com.estoque.sistema.repository.ProductRepository;
import com.estoque.sistema.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final StockMovementRepository stockMovementRepository;
    private final OrderRepository orderRepository;

    public List<LowStockReportDTO> lowStockProducts(Integer threshold) {
        int limit = threshold == null ? 10 : threshold;
        return productRepository.findByStockQuantityLessThanEqual(limit)
                .stream()
                .map(p -> new LowStockReportDTO(p.getId(), p.getName(), p.getStockQuantity(), p.getCategory()))
                .toList();
    }

    public List<TopSellingProductDTO> topSellingProducts(int limit) {
        return orderItemRepository.findTopSellingProducts(PageRequest.of(0, limit));
    }

    public Page<StockMovementDTO> stockMovements(Pageable pageable) {
        return stockMovementRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(sm -> new StockMovementDTO(
                        sm.getId(),
                        sm.getProduct().getId(),
                        sm.getProduct().getName(),
                        sm.getType(),
                        sm.getQuantity(),
                        sm.getReason(),
                        sm.getCreatedAt()
                ));
    }

    public DashboardMetricsDTO dashboardMetrics(Integer lowStockThreshold) {
        int threshold = lowStockThreshold == null ? 10 : lowStockThreshold;
        long totalOrders = orderRepository.count();
        long totalProducts = productRepository.count();
        long lowStockProducts = productRepository.countByStockQuantityLessThanEqual(threshold);
        var revenue = orderRepository.totalRevenue();
        var avg = orderRepository.averageTicket();

        return new DashboardMetricsDTO(totalOrders, totalProducts, lowStockProducts, revenue, avg);
    }
}
