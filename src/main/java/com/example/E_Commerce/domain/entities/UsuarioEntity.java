package com.example.E_Commerce.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.antlr.v4.runtime.misc.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@MappedSuperclass  //no crea una tabla para Usuario, permitiendo que las clases hijas hereden sus atributos -> ESPECIALIZACION
public abstract class UsuarioEntity {
    private String nombre;
    private String correo;
    private String contrasena;
    private String direccion;

}
