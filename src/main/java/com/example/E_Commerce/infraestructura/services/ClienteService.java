package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.api.DTOs.request.cliente.ClienteSolicitudCorreoDTO;
import com.example.E_Commerce.api.DTOs.response.cliente.ClienteRespuestaDTO;
import com.example.E_Commerce.api.DTOs.request.cliente.ClienteSolicitudDTO;
import com.example.E_Commerce.api.DTOs.response.cliente.ClienteRespuestaCorreoDTO;
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


    public ClienteRespuestaDTO agregarCliente(ClienteSolicitudDTO clienteSolicitud){

        if(!correoElectronicoHelper.esValidoCorreoElectornico(clienteSolicitud.getCorreo()))
            throw new ArgumentoIlegalException("el correo electronico no posee un formato valido");

        if(clienteRepository.existsByCorreo(clienteSolicitud.getCorreo()))
            throw new CorreoDuplicadolException("el correo electronico ingresado ya existe");


        UsuarioEntity cliente = UsuarioEntity.builder()
                                             .carrito(new CarritoEntity())
                                             .nombre(clienteSolicitud.getNombre())
                                             .correo(clienteSolicitud.getCorreo())
                                             .contrasena(clienteSolicitud.getContrasena()) // mas adelante implementare security y sus roles en login y logout
                                             .direccion(clienteSolicitud.getDireccion())
                                             .habilitado(true)
                                             .rol(Rol.CLIENTE)
                                             .build();

        //creacion de carrito para setear el usuario
        //CarritoEntity carrito = new CarritoEntity();
        //carrito.setCliente(cliente);

        //ahora seteo el carrito en cliente para asegurar que la relación bidireccional esté completamente sincronizada.
        //cliente.setCarrito(carrito);
cliente.getCarrito().setCliente(cliente);
        clienteRepository.save(cliente);

        return clienteMapper.clienteToClienteDtoRespuesta(cliente);
    }


    //encuentro el cliente y y lo retorno directamente mapeandolo a DTO
    public ClienteRespuestaDTO obtenerClientePorId(UUID id){

        return clienteMapper.clienteToClienteDtoRespuesta(
                clienteRepository.findById(id)
                                 .orElseThrow(() -> new ClienteNoEncontradoException("cliente no encontrado")));
    }


    public ClienteRespuestaDTO actualizarDatosCliente(UUID id, ClienteSolicitudDTO clienteDto){

        UsuarioEntity cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNoEncontradoException("cliente no encontrado"));

        cliente.setNombre((clienteDto.getNombre()));
        cliente.setDireccion(clienteDto.getDireccion());
        clienteRepository.save(cliente);
        return clienteMapper.clienteToClienteDtoRespuesta(cliente);
    }

    public ClienteRespuestaCorreoDTO actualizarCorreoElectronico(UUID id, ClienteSolicitudCorreoDTO correoSolicitud){

        if(!correoElectronicoHelper.esValidoCorreoElectornico(correoSolicitud.getCorreo()))
            throw new ArgumentoIlegalException("el correo electronico no posee un formato valido");

        UsuarioEntity cliente  = clienteRepository.findById(id).orElseThrow(() -> new ClienteNoEncontradoException("cliente no encontrado"));

        if(clienteRepository.existsByCorreo(cliente.getCorreo()))
            throw new CorreoDuplicadolException("debe colocar un correo electronico diferente al registrado");

        cliente.setCorreo(correoSolicitud.getCorreo());
        return clienteMapper.nuevoCorreoToNuevoCorreoDto(correoSolicitud.getCorreo());
    }

    public void deshabilitarUsuario(UUID id) {
        UsuarioEntity cliente = clienteRepository.findById(id).orElseThrow(()-> new ClienteNoEncontradoException("cliente no encontrado"));
        if (cliente.isHabilitado()) {
            cliente.setHabilitado(false);
            clienteRepository.save(cliente);
            correoElectronicoHelper.enviarCorreoCancelacionDeCuenta(cliente.getCorreo(),cliente.getNombre());
        }
    }


    public void habilitarUsuario(UUID id) {
        UsuarioEntity cliente = clienteRepository.findById(id).orElseThrow(()-> new ClienteNoEncontradoException("cliente no encontrado"));
        if (!cliente.isHabilitado()) {
            cliente.setHabilitado(true);
            clienteRepository.save(cliente);
        }
    }

}
