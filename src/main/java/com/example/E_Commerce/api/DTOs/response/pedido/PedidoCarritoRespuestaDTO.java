package com.example.E_Commerce.api.DTOs.response.pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoCarritoRespuestaDTO {
    private String nombre;
    private Float precio;
}
