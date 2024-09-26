package com.example.E_Commerce.domain.repositories;

import com.example.E_Commerce.domain.entities.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<CategoriaEntity,Long> {
}
