package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.api.DTOs.dtoRequest.ClienteDtoSolicitudCorreo;
import com.example.E_Commerce.api.DTOs.dtoResponse.ClienteDtoRespuesta;
import com.example.E_Commerce.api.DTOs.dtoRequest.ClienteDtoSolicitud;
import com.example.E_Commerce.api.DTOs.dtoResponse.ClienteDtoRespuestaCorreo;
import com.example.E_Commerce.domain.Rol;
import com.example.E_Commerce.domain.entities.CarritoEntity;
import com.example.E_Commerce.domain.entities.UsuarioEntity;
import com.example.E_Commerce.domain.helpers.CorreoElectronicoHelper;
import com.example.E_Commerce.domain.mappers.ClienteMapper;
import com.example.E_Commerce.domain.repositories.UsuarioRepository;
import com.example.E_Commerce.infraestructura.exceptions.ClienteNoEncontradoException;
import com.example.E_Commerce.infraestructura.exceptions.CorreoDuplicadolException;
import com.example.E_Commerce.infraestructura.exceptions.ArgumentoIlegalException;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ClienteService {
    private final UsuarioRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final CorreoElectronicoHelper correoElectronicoHelper;

    public ClienteService(UsuarioRepository clienteRepository, ClienteMapper clienteMapper, CorreoElectronicoHelper correoElectronicoHelper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.correoElectronicoHelper = correoElectronicoHelper;
    }


    public ClienteDtoRespuesta agregarCliente(ClienteDtoSolicitud clienteDtoSolicitud){

        if(!correoElectronicoHelper.esValidoCorreoElectornico(clienteDtoSolicitud.getCorreo()))
            throw new ArgumentoIlegalException("el correo electronico no posee un formato valido");

        if(clienteRepository.existsByCorreo(clienteDtoSolicitud.getCorreo()))
            throw new CorreoDuplicadolException("el correo electronico ingresado ya existe");


        UsuarioEntity cliente = UsuarioEntity.builder()
                                             .carrito(new CarritoEntity())
                                             .nombre(clienteDtoSolicitud.getNombre())
                                             .correo(clienteDtoSolicitud.getCorreo())
                                             .contrasena(clienteDtoSolicitud.getContrasena()) // mas adelante implementare security y sus roles en login y logout
                                             .direccion(clienteDtoSolicitud.getDireccion())
                                             .habilitado(true)
                                             .rol(Rol.CLIENTE)
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

        UsuarioEntity cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNoEncontradoException("cliente no encontrado"));

        cliente.setNombre((clienteDto.getNombre()));
        cliente.setDireccion(clienteDto.getDireccion());
        clienteRepository.save(cliente);
        return clienteMapper.ClienteToClienteDtoRespuesta(cliente);
    }

    public ClienteDtoRespuestaCorreo actualizarCorreoElectronico(UUID id, ClienteDtoSolicitudCorreo correoDto){

        if(!correoElectronicoHelper.esValidoCorreoElectornico(correoDto.getCorreo()))
            throw new ArgumentoIlegalException("el correo electronico no posee un formato valido");

        UsuarioEntity cliente  = clienteRepository.findById(id).orElseThrow(() -> new ClienteNoEncontradoException("cliente no encontrado"));

        if(clienteRepository.existsByCorreo(cliente.getCorreo()))
            throw new CorreoDuplicadolException("debe colocar un correo electronico diferente al registrado");

        cliente.setCorreo(correoDto.getCorreo());
        return clienteMapper.nuevoCorreoToNuevoCorreoDto(correoDto.getCorreo());
    }

    public void deshabilitarUsuario(UUID id) {
        UsuarioEntity Usuario = clienteRepository.findById(id).orElseThrow(()-> new ClienteNoEncontradoException("cliente no encontrado"));
        if (Usuario.isHabilitado()) {
            Usuario.setHabilitado(false);
            clienteRepository.save(Usuario);
        }
    }


    public void habilitarUsuario(UUID id) {
        UsuarioEntity Usuario = clienteRepository.findById(id).orElseThrow(()-> new ClienteNoEncontradoException("cliente no encontrado"));
        if (!Usuario.isHabilitado()) {
            Usuario.setHabilitado(true);
            clienteRepository.save(Usuario);
        }
    }

}
