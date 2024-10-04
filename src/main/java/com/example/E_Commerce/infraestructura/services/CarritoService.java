package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.api.DTOs.request.carrito.ProductoCarritoSolicitudDTO;
import com.example.E_Commerce.api.DTOs.response.carrito.ProductoCarritoRespuestaDTO;
import com.example.E_Commerce.domain.entities.CarritoEntity;
import com.example.E_Commerce.domain.entities.ProductoEntity;
import com.example.E_Commerce.domain.entities.UsuarioEntity;
import com.example.E_Commerce.domain.repositories.CarritoRepository;
import com.example.E_Commerce.domain.repositories.ProductoRepository;
import com.example.E_Commerce.domain.repositories.UsuarioRepository;
import com.example.E_Commerce.infraestructura.exceptions.ClienteNoEncontradoException;
import com.example.E_Commerce.infraestructura.exceptions.ProductoNoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository clieteRepository;
    public CarritoService(CarritoRepository carritoRepository, ProductoRepository productoRepository, UsuarioRepository clieteRepository) {
        this.carritoRepository = carritoRepository;
        this.productoRepository = productoRepository;
        this.clieteRepository = clieteRepository;
    }

    @Value("${carrito.max_productos}")
    private int max_productos;
    @Value("${carrito.umbral_descuento}")
    private int umbralDescuento;
    @Value("${carrito.porcentaje_descuento}")
    private double porcentajeDescuento;

    public ProductoCarritoRespuestaDTO agregarProductoCarrito(ProductoCarritoSolicitudDTO productoCarritoSolicitud) {
        //valido si es de origen argetino
        if (!(productoCarritoSolicitud.getPais().contains("AR")))
            throw new IllegalArgumentException("No podemos enviar productos al país especificado. solo disponible para argentina");

        //busco el cliente el cual tiene el carrito asociado
        UsuarioEntity cliente = clieteRepository.findById(productoCarritoSolicitud.getClientId())
                .orElseThrow(() -> new ClienteNoEncontradoException("el cliente no se encontro"));

        //obtengo el carrito mediante el cliente
        CarritoEntity carrito = cliente.getCarrito();


        //valido si el producto realmente existe o esta disponible
        ProductoEntity producto = productoRepository.findById(productoCarritoSolicitud.getProductoId())
                .filter(ProductoEntity::getDisponible)
                .orElseThrow(() -> new ProductoNoEncontradoException("El producto seleccionado no se encuentra disponible"));

        //seteo el producto en el map y la cantidad, utilizo la metodo sum (PF)
        carrito.getProductos().merge(producto, productoCarritoSolicitud.getCantidad(), Integer::sum);


        //guardo el carrito en la entidad cliente el cual se propaga mediante cascada
        cliente.setCarrito(carrito);


        // obtenemos el precio de cada entrada mediante map, y lo multiplicamos por la clave del map, convirtiendolo a BigDecimel
        BigDecimal precioTotal = carrito.getProductos().entrySet()
                .stream()
                .map(entry -> entry.getKey().getPrecio()
                        .multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


         return  ProductoCarritoRespuestaDTO.builder()
                                            .precioTotal(precioTotal)
                                            .productos(carrito.getProductos())
                                            .build();
    }

}