package com.estoque.sistema.dto;

public record TopSellingProductDTO(
        Long productId,
        String productName,
        Long totalSold
) {}
