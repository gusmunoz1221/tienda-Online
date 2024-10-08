package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.api.DTOs.request.carrito.ProductoCarritoSolicitudDTO;
import com.example.E_Commerce.api.DTOs.response.carrito.ProductoCarritoDTO;
import com.example.E_Commerce.api.DTOs.response.carrito.ProductoCarritoRespuestaDTO;
import com.example.E_Commerce.domain.entities.CarritoEntity;
import com.example.E_Commerce.domain.entities.ProductoEntity;
import com.example.E_Commerce.domain.entities.UsuarioEntity;
import com.example.E_Commerce.domain.repositories.CarritoRepository;
import com.example.E_Commerce.domain.repositories.ProductoRepository;
import com.example.E_Commerce.domain.repositories.UsuarioRepository;
import com.example.E_Commerce.infraestructura.exceptions.ArgumentoIlegalException;
import com.example.E_Commerce.infraestructura.exceptions.ClienteNoEncontradoException;
import com.example.E_Commerce.infraestructura.exceptions.ProductoNoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
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
            throw new ArgumentoIlegalException("No podemos enviar productos al país especificado. solo disponible para argentina");

        //busco el cliente el cual tiene el carrito asociado
        UsuarioEntity cliente = clieteRepository.findById(productoCarritoSolicitud.getClientId())
                .orElseThrow(() -> new ClienteNoEncontradoException("el cliente no se encontro"));

        //obtengo el carrito mediante el cliente
        CarritoEntity carrito = cliente.getCarrito();


        //valido si el producto existe y esta disponible
        ProductoEntity producto = productoRepository.findById(productoCarritoSolicitud.getProductoId())
                .filter(ProductoEntity::getDisponible)
                .orElseThrow(() -> new ProductoNoEncontradoException("El producto seleccionado no se encuentra disponible"));


        //seteo producto y la cantidad, utilizo la metodo sum (PF) incrementando la cantidad
       carrito.getProductos().merge(producto,productoCarritoSolicitud.getCantidad(), Integer::sum);


       //persisto el Map en carrito, contiene: idProducto-idCarrito-cantidad
       carritoRepository.save(carrito);


        // Convertir el Map<ProductoEntity, Integer> a List<ProductoCarritoDTO>
        List<ProductoCarritoDTO> productosCarritoDto = carrito.getProductos().entrySet().stream()
                .map(entry -> new ProductoCarritoDTO(
                        entry.getKey().getNombre(),
                        entry.getKey().getPrecio().floatValue()))
                .toList();

        // Calculo el precio total del carrito
        BigDecimal precioTotal = carrito.getProductos()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey()
                        .getPrecio()
                        .multiply(new BigDecimal(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Devolvo el DTO con la lista de productos y el precio total
        return new ProductoCarritoRespuestaDTO(productosCarritoDto, precioTotal);

    }
}