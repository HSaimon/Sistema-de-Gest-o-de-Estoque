package com.estoque.sistema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SistemaGestaoEstoqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaGestaoEstoqueApplication.class, args);
    }
}
