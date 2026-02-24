package com.hsaimon.estoque.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(
        Long totalOrders,
        Long totalProducts,
        BigDecimal totalRevenue,
        Long lowStockProducts,
        List<TopSellingProductDto> topSelling
) {
}
