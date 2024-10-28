package com.example.E_Commerce.domain.repositories;

import com.example.E_Commerce.domain.entities.ProductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ProductoRepository extends JpaRepository<ProductoEntity,Long> {
    boolean existsByNombre(String name);
    Page<ProductoEntity> findByNombreContains(String name, Pageable pageable);
    Page<ProductoEntity> findByNombreContainsAndCategoriaIdAndPrecioBetween(String nombre,
                                                                            Long categoriaId,
                                                                            BigDecimal minimo,
                                                                            BigDecimal maximo,
                                                                            Pageable pageable);
    Page<ProductoEntity> findByCategoriaId(Long categoriaId, Pageable pageable);

}
