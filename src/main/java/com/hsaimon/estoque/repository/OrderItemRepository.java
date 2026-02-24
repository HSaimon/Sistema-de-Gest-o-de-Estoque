package com.hsaimon.estoque.repository;

import com.hsaimon.estoque.dto.StockMovementDto;
import com.hsaimon.estoque.dto.TopSellingProductDto;
import com.hsaimon.estoque.model.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("select new com.hsaimon.estoque.dto.TopSellingProductDto(oi.product.id, oi.product.name, sum(oi.quantity)) " +
            "from OrderItem oi group by oi.product.id, oi.product.name order by sum(oi.quantity) desc")
    List<TopSellingProductDto> findTopSelling(Pageable pageable);

    @Query("select new com.hsaimon.estoque.dto.StockMovementDto(o.createdAt, p.id, p.name, oi.quantity) " +
            "from OrderItem oi join oi.order o join oi.product p order by o.createdAt desc")
    List<StockMovementDto> findStockMovements(Pageable pageable);
}
