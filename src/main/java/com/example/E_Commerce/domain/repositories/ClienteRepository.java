package com.example.E_Commerce.domain.repositories;

import com.example.E_Commerce.domain.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity,Long> {
}
