package com.example.E_Commerce.api.controllers;

import com.example.E_Commerce.api.DTOs.request.producto.ProductoSolicitudDTO;
import com.example.E_Commerce.api.DTOs.response.producto.ProductoRespuestaDTO;
import com.example.E_Commerce.infraestructura.services.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    ResponseEntity<?> agregarProducto(@Valid @RequestBody @Validated ProductoSolicitudDTO productoDto, BindingResult resultado){

        if (resultado.hasErrors()){
            List<FieldError> campoDeErrores = resultado.getFieldErrors();
            Map<String, String> errores = new HashMap<>();

            for (FieldError error : campoDeErrores) {
                errores.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errores);
        }

        return ResponseEntity.ok(productoService.agregarProducto(productoDto));
    }

    @GetMapping("{id}")
    ResponseEntity<ProductoRespuestaDTO> obtenerProducto(@Validated @PathVariable Long id){
        return ResponseEntity.ok(productoService.obtenerProductoDto(id));
    }

    @GetMapping
    ResponseEntity<List<ProductoRespuestaDTO>> obtenerProductos(){
        return ResponseEntity.ok(productoService.obtenerProductos());
    }

    @DeleteMapping
    ResponseEntity<Void> eliminarProducto(@Validated @PathVariable Long id){
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    ResponseEntity<?> modificarProducto(@Valid @Validated @PathVariable Long id, @RequestBody ProductoSolicitudDTO productoDto, BindingResult resultado){

        if (resultado.hasErrors()){
            List<FieldError> campoDeErrores = resultado.getFieldErrors();
            Map<String, String> errores = new HashMap<>();

            for (FieldError error : campoDeErrores) {
                errores.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errores);
        }

        return ResponseEntity.ok(productoService.modificarProducto(id,productoDto));
    }
}
