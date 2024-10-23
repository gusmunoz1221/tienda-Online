package com.example.E_Commerce.api.DTOs.response.pedido;

import com.example.E_Commerce.api.DTOs.response.carrito.ProductoPedidoCarrito;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductosPedidoRespuestaDTO {
    private String numeroDePedido;
    private String fecha;
    private int cantidad;
    private BigDecimal percioTotal;
    private List<PedidoCarritoRespuestaDTO> productos;
}
