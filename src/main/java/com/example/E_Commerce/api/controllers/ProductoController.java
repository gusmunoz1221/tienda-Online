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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

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

    @GetMapping("/filtro")
    public ResponseEntity<List<ProductoRespuestaDTO>> obtenerProductosPorPaginacion(@RequestParam(name = "NumeroDePagina",defaultValue = "0") Integer numeroDePagina,
                                                                                    @RequestParam(name = "TamanoDePagina",defaultValue = "1") Integer tamanoDePagina,
                                                                                    @RequestParam(required = false) String nombre,
                                                                                    @RequestParam(required = false) Long categoriaId,
                                                                                    @RequestParam(required = false) BigDecimal minimo,
                                                                                    @RequestParam(required = false)BigDecimal maximo){

        if(categoriaId==null && minimo==null && maximo==null)
            return ResponseEntity.ok(productoService.buscarProductoPorNombre(numeroDePagina,tamanoDePagina,nombre));

        if(categoriaId!=null && minimo==null && maximo==null)
            return ResponseEntity.ok(productoService.buscarProductoPorCategoria(numeroDePagina,tamanoDePagina,categoriaId));
        else return ResponseEntity.ok(productoService.buscarProductoPorNombreYPrecio(numeroDePagina,tamanoDePagina,minimo,maximo,nombre,categoriaId));
    }

}
