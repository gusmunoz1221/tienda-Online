package com.example.E_Commerce.api.DTOs.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDtoRespuesta {

    private String nombre;
    private String correo;
}
