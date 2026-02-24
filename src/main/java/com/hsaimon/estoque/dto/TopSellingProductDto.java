package com.hsaimon.estoque.dto;

public record TopSellingProductDto(
        Long productId,
        String productName,
        Long totalSold
) {
}
