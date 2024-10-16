package com.example.E_Commerce.api.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoProductoSolicitudDTO {
    UUID id;
    Long idProducto;
    int cantidad;
}
