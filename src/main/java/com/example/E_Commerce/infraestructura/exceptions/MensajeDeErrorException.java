package com.example.E_Commerce.infraestructura.exceptions;

import lombok.Data;

@Data
public class MensajeDeErrorException {
    private String mensaje;
    private int codigo;

    public MensajeDeErrorException(String mensaje, int codigo) {
        this.mensaje=mensaje;
        this.codigo=codigo;
    }
}
