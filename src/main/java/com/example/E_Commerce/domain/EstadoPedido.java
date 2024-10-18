package com.example.E_Commerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
public enum EstadoPedido {
    PENDIENTE,
    EN_PROCESO,
    FALLIDO,
    ENVIADO,
    ENTREGADO
}
