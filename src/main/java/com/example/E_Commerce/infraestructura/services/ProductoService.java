package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.domain.entities.ProductoEntity;
import com.example.E_Commerce.domain.repositories.ProductoRepository;
import com.example.E_Commerce.infraestructura.exceptions.ProductoNoEncontradoException;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoEntity obtenerProductoPorId(Long id){
        return productoRepository.findById(id)
                .filter(ProductoEntity::getDisponible)
                .orElseThrow(() -> new ProductoNoEncontradoException("El producto seleccionado no se encuentra disponible"));
    }
}
