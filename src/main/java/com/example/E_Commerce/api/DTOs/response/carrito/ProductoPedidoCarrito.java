package com.example.E_Commerce.api.DTOs.response.carrito;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoPedidoCarrito {
    private long id;
    private String nombre;
    private BigDecimal precio;
    private int cantidad;
}
