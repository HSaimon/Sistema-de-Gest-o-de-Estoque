package com.hsaimon.estoque.dto;

public record LowStockProductDto(
        Long id,
        String name,
        Integer stockQuantity
) {
}
