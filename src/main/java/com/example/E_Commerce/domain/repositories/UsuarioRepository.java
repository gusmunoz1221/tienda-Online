package com.example.E_Commerce.domain.repositories;

import com.example.E_Commerce.domain.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity,UUID> {
    boolean existsByCorreo(String correo);
    List<UsuarioEntity> findAllByHabilitadoTrue();
}
