package com.estoque.sistema.controller;

import com.estoque.sistema.dto.*;
import com.estoque.sistema.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Relatórios")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/low-stock")
    @Operation(summary = "Relatório de produtos com baixo estoque")
    public List<LowStockReportDTO> lowStock(@RequestParam(required = false) Integer threshold) {
        return reportService.lowStockProducts(threshold);
    }

    @GetMapping("/top-selling")
    @Operation(summary = "Relatório de produtos mais vendidos")
    public List<TopSellingProductDTO> topSelling(@RequestParam(defaultValue = "5") int limit) {
        return reportService.topSellingProducts(limit);
    }

    @GetMapping("/movements")
    @Operation(summary = "Histórico de movimentações")
    public Page<StockMovementDTO> movements(@PageableDefault(size = 20) Pageable pageable) {
        return reportService.stockMovements(pageable);
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Métricas agregadas para dashboard")
    public DashboardMetricsDTO dashboard(@RequestParam(required = false) Integer lowStockThreshold) {
        return reportService.dashboardMetrics(lowStockThreshold);
    }
}
