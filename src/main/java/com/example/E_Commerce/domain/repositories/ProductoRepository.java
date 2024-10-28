package com.example.E_Commerce.domain.repositories;

import com.example.E_Commerce.domain.entities.ProductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoEntity,Long> {
    boolean existsByNombre(String name);
    Page<ProductoEntity> findByNombreContains(String name, Pageable pageable);
    Page<ProductoEntity> findByPrecioBetweenAndCategoriaId(double minimo, double maximo,Long categoriaId,Pageable pageable);

    Page<ProductoEntity> findByCategoriaId(Long categoriaId, Pageable pageable);

    Page<ProductoEntity> findByNombreContainsAndCategoriaIdAndPrecioBetween(String nombre,
                                                                                    Long categoriaId,
                                                                                    Double precio);
}
