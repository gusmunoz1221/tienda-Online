package com.example.E_Commerce.domain.entities;

import com.example.E_Commerce.domain.Rol;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "cliente")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nombre;
    private String correo;
    private String contrasena;
    private String direccion;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    boolean habilitado;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private CarritoEntity carrito;

    @OneToMany(
            mappedBy = "cliente",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY) // evita carga innecesaria de datos cuando no son requeridos.)
    private List<PedidoEntity> pedidos;


}
