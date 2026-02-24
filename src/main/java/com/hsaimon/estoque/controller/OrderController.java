package com.hsaimon.estoque.controller;

import com.hsaimon.estoque.dto.OrderRequest;
import com.hsaimon.estoque.dto.OrderResponse;
import com.hsaimon.estoque.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastro de pedidos com baixa de estoque")
    public OrderResponse create(@Valid @RequestBody OrderRequest request) {
        return orderService.create(request);
    }
}
