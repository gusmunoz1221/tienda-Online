package com.example.E_Commerce.domain.entities;

import com.example.E_Commerce.domain.EstadoPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "envio")
public class EnvioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccionEnvio;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estadoPedido;


    @OneToOne(mappedBy = "envio", cascade=CascadeType.ALL)
    private PedidoEntity pedido;

}
