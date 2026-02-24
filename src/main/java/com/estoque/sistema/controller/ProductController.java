package com.estoque.sistema.controller;

import com.estoque.sistema.dto.ProductRequestDTO;
import com.estoque.sistema.dto.ProductResponseDTO;
import com.estoque.sistema.service.ProductService;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Produtos")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Lista produtos com paginação")
    public Page<ProductResponseDTO> findAll(@RequestParam(required = false) String name,
                                            @PageableDefault(size = 10) Pageable pageable) {
        return productService.findAll(name, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca produto por ID")
    public ProductResponseDTO findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Cria um novo produto")
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um produto")
    public ProductResponseDTO update(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO dto) {
        return productService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um produto")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
