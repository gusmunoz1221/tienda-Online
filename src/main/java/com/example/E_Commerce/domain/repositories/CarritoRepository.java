package com.example.E_Commerce.domain.repositories;

import com.example.E_Commerce.domain.entities.CarritoEntity;
import com.example.E_Commerce.domain.entities.ProductoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface CarritoRepository extends JpaRepository<CarritoEntity,Long> {
   /* @Modifying
   // @Transactional
   // @Query("UPDATE CarritoEntity c SET c.productos = MERGE(c.productos, :productos) WHERE c.id = :id")
    //void eliminarProductosDelCarrito(Long id, Map<ProductoEntity, Integer> productos);
*/
}
