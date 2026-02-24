package com.estoque.sistema.dto;

import java.math.BigDecimal;

public record DashboardMetricsDTO(
        Long totalOrders,
        Long totalProducts,
        Long lowStockProducts,
        BigDecimal totalRevenue,
        Double averageTicket
) {}
