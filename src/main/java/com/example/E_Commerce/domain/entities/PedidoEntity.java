package com.example.E_Commerce.domain.entities;

import com.example.E_Commerce.domain.EstadoPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private LocalDateTime fecha;
    private String numeroDePedido;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private UsuarioEntity cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductoPedidoEntity> productos;

    @OneToOne(cascade = CascadeType.ALL)
    private PagoEntity pago;

    @OneToOne(cascade = CascadeType.ALL)
    private EnvioEntity envio;

    public String obtenerFechaFormateada() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
        return fecha.format(formato);
    }
}
