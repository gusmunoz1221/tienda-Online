package com.example.E_Commerce.api.DTOs.request.producto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoSolicitudDTO {
    @NotBlank(message = "el nombre del producto es obligatorio")
    @Size(min = 5, max = 30,message = "la longitud minima del nombre es 3(tres)")
    private String nombre;
    @NotBlank(message = "la descripcion del producto es obligatorio")
    @Size(min = 5, max = 50,message = "la longitud minima de la descripcion es 6(seis)")
    private String descripcion;
    @NotNull(message = "debe incluir el precio del producto")
    @Positive(message = "el precio debe ser un numero positivo")
    private BigDecimal precio;
    @Positive(message = "el stock debe ser un numero positivo")
    private Integer stock;
    @NotNull(message = "se requiere el campo idcategoria")
    private Long categoriaId;
}
