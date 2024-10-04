package com.example.E_Commerce.api.DTOs.request.carrito;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "debes seleccionar la cantidad de productos")
    @Size(min = 1, max= 10, message = "la cantidad maxima de productos es 10(diez)")
    private int cantidad;
    private String pais;
}
