package com.example.E_Commerce.api.DTOs.request.carrito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarProductoCarritoDTO {
    Long idProducto;
    int cantidad;
}
