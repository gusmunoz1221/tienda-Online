package com.example.E_Commerce.api.DTOs.response.carrito;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoEnCarrito {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private int cantidad;
}
