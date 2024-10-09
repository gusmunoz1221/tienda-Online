package com.example.E_Commerce.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;
    private String numeroDePedido;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private UsuarioEntity cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductoPedidoEntity> productos;

    @OneToOne(cascade = CascadeType.ALL)
    private PagoEntity pago;

    @OneToOne(cascade = CascadeType.ALL)
    private EnvioEntity envio;
}
