package com.example.E_Commerce.domain.repositories;

import com.example.E_Commerce.domain.entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoEntity,Long> {
    boolean existsByNombre(String name);
}
