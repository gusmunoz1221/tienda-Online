package com.example.E_Commerce.domain.mappers;

import com.example.E_Commerce.api.DTOs.dtoResponse.ClienteDtoRespuesta;
import com.example.E_Commerce.api.DTOs.dtoResponse.ClienteDtoRespuestaCorreo;
import com.example.E_Commerce.domain.entities.ClienteEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    public ClienteDtoRespuesta ClienteToClienteDtoRespuesta(ClienteEntity cliente){
        ClienteDtoRespuesta clienteDtoRespuesta = new ClienteDtoRespuesta();
        BeanUtils.copyProperties(cliente,clienteDtoRespuesta);
        return clienteDtoRespuesta;
    }

    public ClienteDtoRespuestaCorreo nuevoCorreoToNuevoCorreoDto(String correo){
        ClienteDtoRespuestaCorreo correoDto = new ClienteDtoRespuestaCorreo();
        correoDto.setCorreo(correo);
        return correoDto;
    }
}
