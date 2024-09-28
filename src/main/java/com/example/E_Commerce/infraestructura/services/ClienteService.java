package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.api.DTOs.dtoResponse.ClienteDtoRespuesta;
import com.example.E_Commerce.api.DTOs.dtoRequest.ClienteDtoSolicitud;
import com.example.E_Commerce.api.DTOs.dtoResponse.ClienteDtoRespuestaCorreo;
import com.example.E_Commerce.domain.entities.CarritoEntity;
import com.example.E_Commerce.domain.entities.ClienteEntity;
import com.example.E_Commerce.domain.helpers.CorreoElectronicoHelper;
import com.example.E_Commerce.domain.mappers.ClienteMapper;
import com.example.E_Commerce.domain.repositories.ClienteRepository;
import com.example.E_Commerce.infraestructura.exceptions.ClienteNoEncontradoException;
import com.example.E_Commerce.infraestructura.exceptions.CorreoDuplicadolException;
import com.example.E_Commerce.infraestructura.exceptions.ArgumentoIlegalException;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final CorreoElectronicoHelper correoElectronicoHelper;
    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper, CorreoElectronicoHelper correoElectronicoHelper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.correoElectronicoHelper = correoElectronicoHelper;
    }


    public ClienteDtoRespuesta crearCliente(ClienteDtoSolicitud clienteDtoSolicitud){

        if(!correoElectronicoHelper.esValidoCorreoElectornico(clienteDtoSolicitud.getCorreo()))
            throw new ArgumentoIlegalException("el correo electronico no posee un formato valido");

        if(clienteRepository.existsByCorreoElectronico(clienteDtoSolicitud.getCorreo()))
            throw new CorreoDuplicadolException("el correo electronico ingresado ya existe");


        ClienteEntity cliente = ClienteEntity.builder()
                                             .id(UUID.randomUUID())
                                             .carrito(new CarritoEntity())
                                             .nombre(clienteDtoSolicitud.getNombre())
                                             .correo(clienteDtoSolicitud.getCorreo())
                                             .contrasena(clienteDtoSolicitud.getContrasena())
                                             .direccion(clienteDtoSolicitud.getDireccion())
                                             .build();

        clienteRepository.save(cliente);

        return clienteMapper.ClienteToClienteDtoRespuesta(cliente);
    }


    //encuentro el cliente y y lo retorno directamente mapeandolo a DTO
    public ClienteDtoRespuesta obtenerClientePorId(UUID id){

        return clienteMapper.ClienteToClienteDtoRespuesta(
                clienteRepository.findById(id)
                                 .orElseThrow(() -> new ClienteNoEncontradoException("cliente no encontrado")));
    }


    public ClienteDtoRespuesta actualizarDatosCliente(UUID id, ClienteDtoSolicitud clienteDto){

        ClienteEntity cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNoEncontradoException("cliente no encontrado"));

        cliente.setNombre(clienteDto.getNombre());
        cliente.setDireccion(clienteDto.getDireccion());
        clienteRepository.save(cliente);
        return clienteMapper.ClienteToClienteDtoRespuesta(cliente);
    }

    public ClienteDtoRespuestaCorreo actualizarCorreoElectronico(UUID id, String nuevoCorreo){

        if(!correoElectronicoHelper.esValidoCorreoElectornico(nuevoCorreo))
            throw new ArgumentoIlegalException("el correo electronico no posee un formato valido");

        ClienteEntity cliente  = clienteRepository.findById(id).orElseThrow(() -> new ClienteNoEncontradoException("cliente no encontrado"));

        if(clienteRepository.existsByCorreoElectronico(cliente.getCorreo()))
            throw new CorreoDuplicadolException("debe colocar un correo electronico diferente al registrado");

        cliente.setCorreo(nuevoCorreo);
        return clienteMapper.nuevoCorreoToNuevoCorreoDto(nuevoCorreo);
    }
}
