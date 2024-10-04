package com.example.E_Commerce.api.DTOs.response.carrito;

import com.example.E_Commerce.domain.entities.ProductoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoCarritoRespuestaDTO {
    private Map<ProductoEntity, Integer> productos;
    private BigDecimal precioTotal;
}
