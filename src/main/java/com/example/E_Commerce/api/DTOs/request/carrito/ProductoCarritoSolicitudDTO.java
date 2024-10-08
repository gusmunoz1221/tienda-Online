package com.example.E_Commerce.api.DTOs.request.carrito;



import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCarritoSolicitudDTO {
    private Long productoId;
    private UUID clientId;
    @NotNull
    @Min(value= 1, message = "la cantidad minima de productos es 1(uno)")
    @Max(value = 10, message = "la cantidad maxima de productos es 10(diez)")
    private Integer cantidad;
    private String pais;
}
