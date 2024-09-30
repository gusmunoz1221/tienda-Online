package com.example.E_Commerce.api.controllers;

import com.example.E_Commerce.api.DTOs.dtoRequest.ClienteDtoSolicitud;
import com.example.E_Commerce.api.DTOs.dtoRequest.ClienteDtoSolicitudCorreo;
import com.example.E_Commerce.api.DTOs.dtoResponse.ClienteDtoRespuesta;
import com.example.E_Commerce.api.DTOs.dtoResponse.ClienteDtoRespuestaCorreo;
import com.example.E_Commerce.infraestructura.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/cliente")
public class ClienteContorller {
    private final ClienteService clienteService;

    public ClienteContorller(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<?> agregarCliente(@Valid @RequestBody @Validated ClienteDtoSolicitud clienteDto, BindingResult resultado){

        if (resultado.hasErrors()){
            List<FieldError> campoDeErrores = resultado.getFieldErrors();
            Map<String, String> errores = new HashMap<>();

            for (FieldError error : campoDeErrores) {
                errores.put(error.getField(), error.getDefaultMessage());
             }

          return ResponseEntity.badRequest().body(errores);
        }

        return ResponseEntity.ok(clienteService.agregarCliente(clienteDto));
    }

    @PostMapping("{id}")
    ResponseEntity<ClienteDtoRespuesta> obtenerClientePorId(@PathVariable UUID id){
        return ResponseEntity.ok(clienteService.obtenerClientePorId(id));
    }

    @PutMapping("{id}")
    ResponseEntity<ClienteDtoRespuesta> actualizarDatosCliente(@PathVariable UUID id, @Valid @RequestBody ClienteDtoSolicitud clienteDto){
        return ResponseEntity.ok(clienteService.actualizarDatosCliente(id,clienteDto));
    }

    @DeleteMapping("{id}")
    ResponseEntity<ClienteDtoRespuestaCorreo> actualizarCorreoElectronico(@PathVariable UUID id,
                                                                          @Valid @Validated @RequestBody ClienteDtoSolicitudCorreo correoDto){
        return ResponseEntity.ok(clienteService.actualizarCorreoElectronico(id,correoDto));
    }

}
