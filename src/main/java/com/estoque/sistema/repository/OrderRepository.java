package com.estoque.sistema.repository;

import com.estoque.sistema.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select coalesce(sum(o.total), 0) from Order o")
    BigDecimal totalRevenue();

    @Query("select coalesce(avg(o.total), 0) from Order o")
    Double averageTicket();
}
