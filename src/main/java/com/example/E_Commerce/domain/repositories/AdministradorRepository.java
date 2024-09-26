package com.example.E_Commerce.domain.repositories;

import com.example.E_Commerce.domain.entities.AdministradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository<AdministradorEntity,Long> {
}
