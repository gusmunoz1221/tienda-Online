package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.api.DTOs.request.carrito.ModificarProductoCarritoDTO;
import com.example.E_Commerce.api.DTOs.request.carrito.ProductoCarritoSolicitudDTO;
import com.example.E_Commerce.api.DTOs.response.carrito.ProductoCarritoDTO;
import com.example.E_Commerce.api.DTOs.response.carrito.ProductoCarritoRespuestaDTO;
import com.example.E_Commerce.api.DTOs.response.carrito.ProductoPedidoCarrito;
import com.example.E_Commerce.domain.entities.CarritoEntity;
import com.example.E_Commerce.domain.entities.ProductoEntity;
import com.example.E_Commerce.domain.entities.ProductoPedidoEntity;
import com.example.E_Commerce.domain.entities.UsuarioEntity;
import com.example.E_Commerce.domain.repositories.CarritoRepository;
import com.example.E_Commerce.domain.repositories.UsuarioRepository;
import com.example.E_Commerce.infraestructura.exceptions.ArgumentoIlegalException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class CarritoService {

    private final CarritoRepository carritoRepository;

    private final ProductoService productoService;
    private final ClienteService clienteService;
    private final UsuarioRepository clienteRepository;

    public CarritoService(CarritoRepository carritoRepository, ProductoService productoService, ClienteService clienteService, UsuarioRepository clienteRepository) {
        this.carritoRepository = carritoRepository;
        this.productoService = productoService;
        this.clienteService = clienteService;
        this.clienteRepository = clienteRepository;
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
        UsuarioEntity cliente = clienteService.obtenerClientePorId(productoCarritoSolicitud.getClientId());

        //obtengo el carrito mediante el cliente
        CarritoEntity carrito = cliente.getCarrito();

        //valido si el producto existe y esta disponible
        ProductoEntity producto = productoService.obtenerProductoPorId(productoCarritoSolicitud.getProductoId());

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

    public ProductoCarritoRespuestaDTO obtenerCarritoPorId(UUID id){

        UsuarioEntity cliente = clienteService.obtenerClientePorId(id);

        List<ProductoCarritoDTO> productosCarritoDto = cliente.getCarrito().getProductos()
                .entrySet()
                .stream()
                .map(entry -> new ProductoCarritoDTO(
                        entry.getKey().getNombre(),
                        entry.getKey().getPrecio().floatValue()))
                .toList();


        BigDecimal precioTotal = cliente.getCarrito().getProductos()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey()
                        .getPrecio()
                        .multiply(new BigDecimal(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return new ProductoCarritoRespuestaDTO(productosCarritoDto, precioTotal);
    }

    //utilizado desde el pedidoService
    public Pair<List<ProductoPedidoCarrito>,BigDecimal> obtenerProductosCarrito(UsuarioEntity cliente){

        CarritoEntity carrito = cliente.getCarrito();

        List<ProductoPedidoCarrito> productosCarritoDto = carrito.getProductos()
                .entrySet()
                .stream()
                .map(entry -> new ProductoPedidoCarrito(
                        entry.getKey().getId(),
                        entry.getKey().getNombre(),
                        entry.getKey().getPrecio(),
                        entry.getValue()))
                .toList();


        BigDecimal precioTotal = carrito.getProductos()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey()
                        .getPrecio()
                        .multiply(new BigDecimal(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return  Pair.of(productosCarritoDto,precioTotal);
    }

    public void eliminarCarrito(UUID id){

        UsuarioEntity cliente = clienteService.obtenerClientePorId(id);

        CarritoEntity carrito = cliente.getCarrito();

        // Limpio el Map de productos
        carrito.getProductos().clear();

        //Guardo el carrito para que se refleje en la base de datos
        carritoRepository.save(carrito);

    }


    public void eliminarCarritoPorPedido(List<ProductoEntity> productosParaEliminar,UsuarioEntity cliente){

        Map<ProductoEntity,Integer> productos = cliente.getCarrito().getProductos();

        productosParaEliminar.forEach(producto ->
                productos.
                entrySet().
                removeIf(entry -> entry.getKey().equals(producto))
        );

        //libero la lista para que GC limpie el monticulo
        productosParaEliminar=null;

    }

    public ProductoCarritoRespuestaDTO modificarCantidadProducto(UUID id, ModificarProductoCarritoDTO productoDto){

        UsuarioEntity cliente = clienteService.obtenerClientePorId(id);

        CarritoEntity carrito = cliente.getCarrito();

        ProductoEntity producto = productoService.obtenerProductoPorId(productoDto.getIdProducto());

        //se supone que el producto ya esta en el carrito
        carrito.getProductos().put(producto,productoDto.getCantidad());

        carritoRepository.save(carrito);


        List<ProductoCarritoDTO> productosCarritoDto = cliente.getCarrito().getProductos()
                .entrySet()
                .stream()
                .map(entry -> new ProductoCarritoDTO(
                        entry.getKey().getNombre(),
                        entry.getKey().getPrecio().floatValue()))
                .toList();


        BigDecimal precioTotal = cliente.getCarrito().getProductos()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey()
                        .getPrecio()
                        .multiply(new BigDecimal(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return new ProductoCarritoRespuestaDTO(productosCarritoDto, precioTotal);
    }
}