package com.example.E_Commerce.api.controllers;

import com.example.E_Commerce.api.DTOs.request.cliente.ClienteSolicitudDTO;
import com.example.E_Commerce.api.DTOs.request.cliente.ClienteSolicitudCorreoDTO;
import com.example.E_Commerce.api.DTOs.response.cliente.ClienteRespuestaDTO;
import com.example.E_Commerce.api.DTOs.response.cliente.ClienteRespuestaCorreoDTO;
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
    public ResponseEntity<?> agregarCliente(@Valid @RequestBody @Validated ClienteSolicitudDTO clienteDto, BindingResult resultado){

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

    @GetMapping("{id}")
    ResponseEntity<ClienteRespuestaDTO> obtenerClientePorId(@PathVariable UUID id){
        return ResponseEntity.ok(clienteService.obtenerClientePorIdDto(id));
    }

    @PutMapping("{id}")
    ResponseEntity<ClienteRespuestaDTO> actualizarDatosCliente(@PathVariable UUID id, @Valid @RequestBody ClienteSolicitudDTO clienteDto){
        return ResponseEntity.ok(clienteService.actualizarDatosCliente(id,clienteDto));
    }

    @PutMapping("/correo/{id}")
    ResponseEntity<ClienteRespuestaCorreoDTO> actualizarCorreoElectronico(@PathVariable UUID id,
                                                                          @Valid @Validated @RequestBody ClienteSolicitudCorreoDTO correoDto){
        return ResponseEntity.ok(clienteService.actualizarCorreoElectronico(id,correoDto));
    }

    @DeleteMapping("{id}")
    ResponseEntity<Void> DesabilitarUsuario(@PathVariable UUID id){
        clienteService.deshabilitarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
