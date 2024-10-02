package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.api.DTOs.request.carrito.ProductoCarritoSolicitudDTO;
import com.example.E_Commerce.api.DTOs.response.carrito.ProductoCarritoRespuestaDTO;
import com.example.E_Commerce.domain.repositories.CarritoRepository;
import com.example.E_Commerce.domain.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;
    public CarritoService(CarritoRepository carritoRepository, ProductoRepository productoRepository) {
        this.carritoRepository = carritoRepository;
        this.productoRepository = productoRepository;
    }

    @Value("${carrito.max_productos}")
    private int max_productos;
    @Value("${carrito.umbral_descuento}")
    private int umbralDescuento;
    @Value("${carrito.porcentaje_descuento}")
    private double porcentajeDescuento;
    @Value("${carrito.paises_permitidos}")
    private List<String> paisesPermitidos;

    public ProductoCarritoRespuestaDTO agregarProductoCarrito(ProductoCarritoSolicitudDTO productoCarritoSolicitud){

    }


}