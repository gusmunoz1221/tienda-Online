package com.example.E_Commerce.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carrito")
public class CarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private UsuarioEntity cliente;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "carrito_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<ProductoEntity> productos;


   /* @OneToMany(
            mappedBy = "carritoEntity",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,fetch = FetchType.LAZY) // evita carga innecesaria de datos cuando no son requeridos.
    private List<ProductoEntity> productos;*/


}
