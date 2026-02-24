package com.hsaimon.estoque.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        LocalDateTime createdAt,
        BigDecimal total,
        List<OrderItemResponse> items
) {
}
