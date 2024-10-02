package com.example.E_Commerce.api.DTOs.request;

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
public class AdministradorSolicitudDTO {

    @Size(min = 5, max = 30,message = "la longitud minima del nombre es 3(tres)")
    private String nombre;

    @Email(message = "correo electronico invalido")
    @NotBlank(message = "correo electronico es obligatorio")
    @Size(min = 5, max = 30,message = "la longitud minima del correo es 5(cinco)")
    @Column(unique = true)//garantiza que cada usuario tenga un correo unico
    private String correo;

    @Size(min = 5, max = 30,message = "la longitud minima de la contraseña es 5(cinco), maxima(dieciseis)")
    private String contrasena;
}
