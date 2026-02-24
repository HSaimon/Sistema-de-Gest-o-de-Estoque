package com.hsaimon.estoque.controller;

import com.hsaimon.estoque.dto.ProductRequest;
import com.hsaimon.estoque.dto.ProductResponse;
import com.hsaimon.estoque.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastro de produtos")
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return productService.create(request);
    }

    @GetMapping
    @Operation(summary = "Listagem paginada de produtos")
    public Page<ProductResponse> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category) {
        return productService.list(PageRequest.of(page, size), category);
    }
}
