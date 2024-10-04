package com.example.E_Commerce.domain.repositories;

import com.example.E_Commerce.domain.entities.CarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<CarritoEntity,Long> {
}
