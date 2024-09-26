package com.example.E_Commerce.domain.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cliente")
public class ClienteEntity extends UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    private CarritoEntity carrito;

    @OneToMany(
            mappedBy = "cliente",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY) // evita carga innecesaria de datos cuando no son requeridos.)
    private List<PedidoEntity> pedidos;
}
