package com.example.E_Commerce.api.DTOs.response.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteRespuestaDTO {

    private String nombre;
    private String correo;
}
