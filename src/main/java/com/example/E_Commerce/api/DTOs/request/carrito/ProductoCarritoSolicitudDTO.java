package com.example.E_Commerce.api.DTOs.request.carrito;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoCarritoSolicitudDTO {
    private Long productoId;
    private UUID clientId;
    private int cantidad;
}
