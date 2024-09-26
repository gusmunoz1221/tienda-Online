package com.example.E_Commerce.domain.repositories;

import com.example.E_Commerce.domain.entities.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoEntity,Long> {
}
