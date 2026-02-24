package com.estoque.sistema.controller;

import com.estoque.sistema.dto.OrderRequestDTO;
import com.estoque.sistema.dto.OrderResponseDTO;
import com.estoque.sistema.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Pedidos")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Registra um pedido e atualiza estoque automaticamente")
    public ResponseEntity<OrderResponseDTO> create(@RequestBody @Valid OrderRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(dto));
    }

    @GetMapping
    @Operation(summary = "Lista pedidos com paginação")
    public Page<OrderResponseDTO> findAll(@PageableDefault(size = 10) Pageable pageable) {
        return orderService.findAll(pageable);
    }
}
