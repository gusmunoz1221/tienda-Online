package com.example.E_Commerce.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "cliente")
public class ClienteEntity extends UsuarioEntity {
    @Id
    private UUID id;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    private CarritoEntity carrito;

    @OneToMany(
            mappedBy = "cliente",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY) // evita carga innecesaria de datos cuando no son requeridos.)
    private List<PedidoEntity> pedidos;


}
