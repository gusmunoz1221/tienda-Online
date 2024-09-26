package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.domain.repositories.CarritoRepository;
import com.example.E_Commerce.domain.repositories.ClienteRepository;

public class UsuarioService {
    private final ClienteRepository clienteRepository;
    public final CarritoRepository carritoRepository;
    public UsuarioService(ClienteRepository clienteRepository, CarritoRepository carritoRepository) {
        this.clienteRepository = clienteRepository;
        this.carritoRepository = carritoRepository;
    }


}
