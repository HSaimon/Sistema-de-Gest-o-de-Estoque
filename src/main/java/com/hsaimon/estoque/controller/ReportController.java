package com.hsaimon.estoque.controller;

import com.hsaimon.estoque.dto.*;
import com.hsaimon.estoque.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reports/low-stock")
    @Operation(summary = "Produtos com baixo estoque")
    public List<LowStockProductDto> lowStock(@RequestParam(defaultValue = "10") int threshold) {
        return reportService.lowStock(threshold);
    }

    @GetMapping("/reports/top-selling")
    @Operation(summary = "Produtos mais vendidos")
    public List<TopSellingProductDto> topSelling(@RequestParam(defaultValue = "5") int limit) {
        return reportService.topSelling(limit);
    }

    @GetMapping("/reports/movements")
    @Operation(summary = "Histórico de movimentações")
    public List<StockMovementDto> movements(@RequestParam(defaultValue = "20") int limit) {
        return reportService.movements(limit);
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Métricas agregadas para dashboard")
    public DashboardResponse dashboard() {
        return reportService.dashboard();
    }
}
