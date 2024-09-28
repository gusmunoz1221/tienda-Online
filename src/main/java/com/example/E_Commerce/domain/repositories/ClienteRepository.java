package com.example.E_Commerce.domain.repositories;

import com.example.E_Commerce.domain.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<ClienteEntity,UUID> {
    boolean existsByCorreoElectronico(String correo);
}
