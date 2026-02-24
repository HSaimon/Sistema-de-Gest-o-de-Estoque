package com.estoque.sistema.dto;

import com.estoque.sistema.model.MovementType;

import java.time.LocalDateTime;

public record StockMovementDTO(
        Long id,
        Long productId,
        String productName,
        MovementType type,
        Integer quantity,
        String reason,
        LocalDateTime createdAt
) {}
