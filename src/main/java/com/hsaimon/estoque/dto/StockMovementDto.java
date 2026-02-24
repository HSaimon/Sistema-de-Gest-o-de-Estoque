package com.hsaimon.estoque.dto;

import java.time.LocalDateTime;

public record StockMovementDto(
        LocalDateTime createdAt,
        Long productId,
        String productName,
        Integer quantityOut
) {
}
