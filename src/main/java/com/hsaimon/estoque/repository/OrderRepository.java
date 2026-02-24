package com.hsaimon.estoque.repository;

import com.hsaimon.estoque.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("select coalesce(sum(o.total),0) from OrderEntity o")
    BigDecimal getTotalRevenue();
}
