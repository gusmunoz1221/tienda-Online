package com.example.E_Commerce.api.controllers;


import com.example.E_Commerce.api.DTOs.request.carrito.ProductoCarritoSolicitudDTO;
import com.example.E_Commerce.infraestructura.services.CarritoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @PostMapping
    public ResponseEntity<?> agregarProductoCarrito(@Valid @RequestBody @Validated ProductoCarritoSolicitudDTO producto, BindingResult resultado){

        if(resultado.hasErrors()){
            List<FieldError> campoDeErrores = resultado.getFieldErrors();
            Map<String,String> errores = new HashMap<>();

            for(FieldError error : campoDeErrores)
                errores.put(error.getField(),error.getDefaultMessage());

         return ResponseEntity.badRequest().body(errores);
        }

        return ResponseEntity.ok(carritoService.agregarProductoCarrito(producto));
    }
}
