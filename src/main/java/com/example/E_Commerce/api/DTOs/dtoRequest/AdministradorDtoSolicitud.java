package com.example.E_Commerce.api.DTOs.dtoRequest;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdministradorDtoSolicitud {

    private String nombre;

    @Email(message = "correo electronico invalido")
    @NotBlank(message = "correo electronico es obligatorio")
    @Size(min = 5, max = 10,message = "la longitud minima del correo es 5, maxima 12")
    @Column(unique = true)//garantiza que cada usuario tenga un correo unico
    private String correoElectronico;

    @Size(min = 5, max = 10,message = "la longitud minima de la contraseña es 5, maxima 12")
    private String contraseña;
}
