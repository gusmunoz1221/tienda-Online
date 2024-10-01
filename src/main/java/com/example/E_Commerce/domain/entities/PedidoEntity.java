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
    private Integer numeroDePedido;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private UsuarioEntity cliente;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY) // evita carga innecesaria de datos cuando no son requeridos.
    @JoinColumn(name = "pedido_id")
    private List<ProductoEntity> productos;

    @OneToOne(cascade = CascadeType.ALL)
    private PagoEntity pago;

    @OneToOne(cascade = CascadeType.ALL)
    private EnvioEntity envio;
}
