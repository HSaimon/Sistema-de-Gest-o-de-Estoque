package com.estoque.sistema.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequestDTO(
        @NotBlank String customerName,
        @Valid @NotEmpty List<OrderItemRequestDTO> items
) {}
