package com.example.E_Commerce.api.controllers;

import com.example.E_Commerce.api.DTOs.response.pedido.ProductosPedidoRespuestaDTO;
import com.example.E_Commerce.infraestructura.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("{id}")
    ResponseEntity<ProductosPedidoRespuestaDTO> agregarPedidoCarrito(@Validated @PathVariable UUID id){
        return ResponseEntity.ok(pedidoService.agregarPedidoCarrito(id));
    }
}
