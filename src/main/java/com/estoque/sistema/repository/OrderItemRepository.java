package com.estoque.sistema.repository;

import com.estoque.sistema.dto.TopSellingProductDTO;
import com.estoque.sistema.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("""
            select new com.estoque.sistema.dto.TopSellingProductDTO(
                oi.product.id,
                oi.product.name,
                sum(cast(oi.quantity as long))
            )
            from OrderItem oi
            group by oi.product.id, oi.product.name
            order by sum(oi.quantity) desc
            """)
    List<TopSellingProductDTO> findTopSellingProducts(org.springframework.data.domain.Pageable pageable);
}
