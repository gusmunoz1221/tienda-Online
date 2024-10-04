package com.example.E_Commerce.domain.mappers;

import com.example.E_Commerce.api.DTOs.response.cliente.ClienteRespuestaDTO;
import com.example.E_Commerce.api.DTOs.response.cliente.ClienteRespuestaCorreoDTO;
import com.example.E_Commerce.domain.entities.UsuarioEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    public ClienteRespuestaDTO clienteToClienteDtoRespuesta(UsuarioEntity cliente){
        ClienteRespuestaDTO clienteDtoRespuesta = new ClienteRespuestaDTO();
        BeanUtils.copyProperties(cliente,clienteDtoRespuesta);
        return clienteDtoRespuesta;
    }

    public ClienteRespuestaCorreoDTO nuevoCorreoToNuevoCorreoDto(String correo){
        ClienteRespuestaCorreoDTO correoDto = new ClienteRespuestaCorreoDTO();
        correoDto.setCorreo(correo);
        return correoDto;
    }
}
