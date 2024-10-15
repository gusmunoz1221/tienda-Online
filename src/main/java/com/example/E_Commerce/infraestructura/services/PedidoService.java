package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.api.DTOs.response.carrito.ProductoPedidoCarrito;
import com.example.E_Commerce.api.DTOs.response.pedido.PedidoCarritoRespuestaDTO;
import com.example.E_Commerce.api.DTOs.response.pedido.ProductosPedidoRespuestaDTO;
import com.example.E_Commerce.domain.entities.*;
import com.example.E_Commerce.domain.repositories.PedidoRepository;
import com.example.E_Commerce.domain.repositories.ProductoRepository;
import com.example.E_Commerce.domain.repositories.SecuenciaPedidoRepository;
import com.example.E_Commerce.infraestructura.exceptions.ProductoNoEncontradoException;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CarritoService carritoService;
    private final ProductoService productoService;
    private final ClienteService clienteService;
    private final SecuenciaPedidoRepository secuenciaPedidoRepository;
    private final ProductoRepository productoRepository;
    public PedidoService(PedidoRepository pedidoRepository, CarritoService carritoService, ProductoService productoService, ClienteService clienteService, SecuenciaPedidoRepository secuenciaPedidoRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.carritoService = carritoService;
        this.productoService = productoService;
        this.clienteService = clienteService;
        this.secuenciaPedidoRepository = secuenciaPedidoRepository;
        this.productoRepository = productoRepository;
    }

    public ProductosPedidoRespuestaDTO agregarPedidoCarrito(UUID id) {

        UsuarioEntity cliente = clienteService.obtenerClientePorId(id);

        PedidoEntity pedido = new PedidoEntity();

        //obtengo la lista del carrito junto con el precio total de la lista
        Pair<List<ProductoPedidoCarrito>, BigDecimal> datosProductos = carritoService.obtenerProductosCarrito(cliente);

        //separo la tupla
        List<ProductoPedidoCarrito> productosCarrito = datosProductos.getLeft();
        BigDecimal precioTotal = datosProductos.getRight();


        //seteo el cliente al pedido junto con la fecha que lo realizo
        pedido.setCliente(cliente);
        pedido.setFecha(new Date());

        String numeroPedido = generarNumeroPedido();

        pedido.setNumeroDePedido(numeroPedido);

        //creo la lista que voy a voy a persistir en ProductoPedidoEntity, que contiene todos los productos pedidos
        List<ProductoPedidoEntity> productosPedido = new ArrayList<>();

        //contador de productos para no hacer nueva consulta a BD
        int cantidadDeProductosEnPedido = 0;


        //productosParaEliminiar son todos los prodcutos que se van a vaciar en el carrito
        List<ProductoEntity> productosParaEliminar = new ArrayList<>();

        //productos no disponibles:
        List<String> productosNoDisponibles = new ArrayList<>();

        //recorro la lista de productos obtenida del carrito
        for (ProductoPedidoCarrito productoCarrito : productosCarrito) {

            //obtengo el producto mediante el id del producto guardado en la lista
            ProductoEntity producto = productoService.obtenerProductoPorId(productoCarrito.getId());

            if (!productoService.estaDisponible(producto, productoCarrito.getCantidad()))
                productosNoDisponibles.add("El producto " + producto.getNombre() + " no tiene stock suficiente. Stock disponible: " + producto.getStock());
            else {
                //  throw new ProductoNoEncontradoException("lo sentimos. el producto: " + producto.getNombre() + " no esta disponible temporalmente");
                // }


                productosParaEliminar.add(producto);

                ProductoPedidoEntity productoPedido = ProductoPedidoEntity.builder()
                        .pedido(pedido)
                        .producto(producto)
                        .cantidad(productoCarrito.getCantidad())
                        .build();

                //cuanto la cantidad de productos en el carrito
                cantidadDeProductosEnPedido += productoCarrito.getCantidad();

                //seteo la entity de producto en producto_pedido
                productosPedido.add(productoPedido);

                ProductosPedidoRespuestaDTO.builder()
                        .numeroDePedido(pedido.getNumeroDePedido())
                        .percioTotal(precioTotal);
            }

        }

        if (!productosNoDisponibles.isEmpty())
            // Si hay problemas, lanzo una excepción con la lista de productos no disponibles
            throw new ProductoNoEncontradoException("Problemas con algunos productos: " + String.join(", ", productosNoDisponibles));

        pedido.setProductos(productosPedido);

        //elimino los productos del carrito
        carritoService.eliminarCarritoPorPedido(productosParaEliminar, cliente);
        pedidoRepository.save(pedido);


        // creamos un nuevo dto que contiene una lista de PedidoCarritoRespuestaDTO
        // mapeamos una lista de productos entities a una lista PedidoCarritoRespuestaDTO;
        return new ProductosPedidoRespuestaDTO(pedido.getNumeroDePedido(), pedido.getFecha(), cantidadDeProductosEnPedido, precioTotal,
                pedido.getProductos()
                        .stream()
                        .map(producto -> new PedidoCarritoRespuestaDTO(
                                producto.getProducto().getNombre(),
                                producto.getProducto().getPrecio().floatValue()))
                        .toList()
        );
    }

    public String generarNumeroPedido() {
        SecuenciaPedidoEntity secuencia = secuenciaPedidoRepository.findById(1L).orElseThrow();
        secuencia.setUltimoNumero(secuencia.getUltimoNumero() + 1);
        secuenciaPedidoRepository.save(secuencia);

        // Formato alfanumérico, 6 dígitos
        return "PED-" + String.format("%06d", secuencia.getUltimoNumero());
    }
}
