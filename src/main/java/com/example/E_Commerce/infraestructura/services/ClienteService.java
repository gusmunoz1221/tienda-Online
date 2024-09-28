package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.api.DTOs.dtoRequest.ClienteDtoRespuesta;
import com.example.E_Commerce.api.DTOs.dtoRequest.ClienteDtoSolicitud;
import com.example.E_Commerce.domain.entities.CarritoEntity;
import com.example.E_Commerce.domain.entities.ClienteEntity;
import com.example.E_Commerce.domain.mappers.ClienteMapper;
import com.example.E_Commerce.domain.repositories.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public ClienteDtoRespuesta crearCliente(ClienteDtoSolicitud clienteDtoSolicitud){
//
        ClienteEntity clienteEntity = ClienteEntity.builder()
                .id(UUID.randomUUID())
                .carrito(new CarritoEntity())
                .nombre(clienteDtoSolicitud.getNombre())
                .correoElectronico(clienteDtoSolicitud.getCorreoElectronico())
                .contrasena(clienteDtoSolicitud.getContraseña())
                .direccion(clienteDtoSolicitud.getDireccion())
                .build();
        clienteRepository.save(clienteEntity);
        return clienteMapper.ClienteToClienteDtoRespuesta(clienteEntity);



    }

}
