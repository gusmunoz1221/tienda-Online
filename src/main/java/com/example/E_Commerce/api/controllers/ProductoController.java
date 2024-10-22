package com.example.E_Commerce.api.controllers;

import com.example.E_Commerce.api.DTOs.request.producto.ProdutoSolicitudDTO;
import com.example.E_Commerce.api.DTOs.response.producto.ProductoRespuestaDTO;
import com.example.E_Commerce.infraestructura.services.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    ResponseEntity<?> agregarProducto(@Valid @Validated ProdutoSolicitudDTO productoDto, BindingResult resultado){

        if(resultado.hasErrors()){
            List<FieldError> campoDeErrores = resultado.getFieldErrors();
            Map<String,String> errores = new HashMap<>();

        //probamos con stram en vez de foreach
            campoDeErrores.stream().map(error-> errores.put(error.getField(),error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(errores);
        }

        return ResponseEntity.ok(productoService.agregarProducto(productoDto));
    }

    @GetMapping("{id}")
    ResponseEntity<ProductoRespuestaDTO> obtenerProducto(@Validated Long id){
        return ResponseEntity.ok(productoService.obtenerProductoDto(id));
    }

    @GetMapping
    ResponseEntity<List<ProductoRespuestaDTO>> obtenerProductos(){
        return ResponseEntity.ok(productoService.obtenerProductos());
    }

    @DeleteMapping
    ResponseEntity<Void> eliminarProducto(@Validated Long id){
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
