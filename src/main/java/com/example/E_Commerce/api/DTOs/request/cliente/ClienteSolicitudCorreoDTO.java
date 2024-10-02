package com.example.E_Commerce.api.DTOs.request.cliente;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteSolicitudCorreoDTO {

    @Email(message = "correo electronico invalido")
    @NotBlank(message = "correo electronico es obligatorio")
    @Size(min = 12, max = 30,message = "la longitud minima del correo es 5(cinco)")
    @Column(unique = true)//garantiza que cada usuario tenga un correo unico
    private String correo;
}
