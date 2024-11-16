package com.example.E_Commerce.domain.repositories;

import com.example.E_Commerce.domain.entities.ProductoEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ProductoRepository extends JpaRepository<ProductoEntity,Long> {

    //obtenemos el producto con bloqueo para evitar las condiciones de carrera y asi se modifique el stock
    //cuando un usuario termine de procesar la compra, es uno a la vez
    @Lock(LockModeType.PESSIMISTIC_WRITE)  // Esto indica que se usará un bloqueo pesimista
    @Query("SELECT p FROM ProductoEntity p WHERE p.id = :id")
    ProductoEntity obtenerProductoConBloqueo(@Param("id") Long id);

    boolean existsByNombre(String name);
    Page<ProductoEntity> findByNombreContains(String name, Pageable pageable);
    Page<ProductoEntity> findByNombreContainsAndCategoriaIdAndPrecioBetween(String nombre,
                                                                            Long categoriaId,
                                                                            BigDecimal minimo,
                                                                            BigDecimal maximo,
                                                                            Pageable pageable);
    Page<ProductoEntity> findByCategoriaId(Long categoriaId, Pageable pageable);

}
