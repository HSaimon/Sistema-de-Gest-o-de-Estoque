package com.estoque.sistema.repository;

import com.estoque.sistema.model.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    Page<StockMovement> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
