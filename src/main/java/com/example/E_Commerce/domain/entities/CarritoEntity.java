package com.example.E_Commerce.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "carrito")
public class CarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private UsuarioEntity cliente;

    //simula una tabla intermedia para poder manejar la cantidad en cada productos
    //crea una tabla llamada producto_carrito, que contiene el id de cada product (no repetido) que se agrega al carrito
    // y un campo llamado cantidad que almacena la cantidad de cada product
    @ElementCollection
    @CollectionTable(name = "productos_carrito", joinColumns = @JoinColumn(name = "carrito_id"))
    @MapKeyJoinColumn(name = "producto_id")
    @Column(name = "cantidad")
    private Map<ProductoEntity, Integer> productos = new HashMap<>();

}

 /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private int cantidadMaxProductos;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "carrito_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<ProductoEntity> productos = new ArrayList<>();
    */