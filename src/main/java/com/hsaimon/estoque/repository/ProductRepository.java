package com.hsaimon.estoque.repository;

import com.hsaimon.estoque.dto.LowStockProductDto;
import com.hsaimon.estoque.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select new com.hsaimon.estoque.dto.LowStockProductDto(p.id, p.name, p.stockQuantity) " +
            "from Product p where p.stockQuantity <= :threshold order by p.stockQuantity asc")
    List<LowStockProductDto> findLowStockProducts(int threshold);

    Page<Product> findAllByCategoryIgnoreCase(String category, Pageable pageable);
}
