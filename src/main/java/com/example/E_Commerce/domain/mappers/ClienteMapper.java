package com.example.E_Commerce.domain.mappers;

import com.example.E_Commerce.api.DTOs.dtoRequest.ClienteDtoRespuesta;
import com.example.E_Commerce.domain.entities.ClienteEntity;
import org.hibernate.annotations.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    public ClienteDtoRespuesta ClienteToClienteDtoRespuesta(ClienteEntity cliente){
        ClienteDtoRespuesta clienteDtoRespuesta = new ClienteDtoRespuesta();
        BeanUtils.copyProperties(cliente,clienteDtoRespuesta);
        return clienteDtoRespuesta;
    }
}
