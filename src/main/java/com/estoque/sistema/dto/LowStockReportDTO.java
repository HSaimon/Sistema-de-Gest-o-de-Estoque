package com.estoque.sistema.dto;

public record LowStockReportDTO(
        Long productId,
        String productName,
        Integer stockQuantity,
        String category
) {}
