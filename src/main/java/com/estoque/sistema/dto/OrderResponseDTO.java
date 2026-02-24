package com.estoque.sistema.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        String customerName,
        BigDecimal total,
        LocalDateTime createdAt,
        List<OrderItemResponseDTO> items
) {}
