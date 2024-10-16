package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.api.DTOs.request.producto.ProdutoSolicitudDTO;
import com.example.E_Commerce.api.DTOs.response.producto.ProductoRespuestaDTO;
import com.example.E_Commerce.domain.entities.CategoriaEntity;
import com.example.E_Commerce.domain.entities.ProductoEntity;
import com.example.E_Commerce.domain.repositories.ProductoRepository;
import com.example.E_Commerce.infraestructura.exceptions.ProductoNoEncontradoException;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;

    public ProductoService(ProductoRepository productoRepository, CategoriaService categoriaService) {
        this.productoRepository = productoRepository;
        this.categoriaService = categoriaService;
    }
    

    public ProductoRespuestaDTO agregarProducto(ProdutoSolicitudDTO productoSolicitud){

      CategoriaEntity categoria = categoriaService.obtenerCategoriaPorId(productoSolicitud.getCategoriaId());

     ProductoEntity producto = ProductoEntity.builder()
             .nombre(productoSolicitud.getNombre())
             .descripcion(productoSolicitud.getDescripcion())
             .precio(productoSolicitud.getPrecio())
             .stock(productoSolicitud.getStock())
             .categoria(categoria).build();

    return (ProductoRespuestaDTO.builder().nombre(producto.getNombre())
             .descripcion(producto.getDescripcion())
             .precio(producto.getPrecio())
             .stock(producto.getStock())
             .categoria(producto.getCategoria())).build();
    }
    public ProductoEntity obtenerProductoPorId(Long id){
        return productoRepository.findById(id)
                .filter(ProductoEntity::getDisponible)
                .orElseThrow(() -> new ProductoNoEncontradoException("El producto seleccionado no se encuentra disponible"));
    }

    public boolean estaDisponible(ProductoEntity producto, int cantidad) {
        // Primero verificamos si el producto está disponible
        if (!producto.getDisponible()){
            return false;
        }

        // Verificamos si hay stock suficiente
        if (producto.getStock() >= cantidad){

            producto.setStock(producto.getStock() - cantidad);


            if (producto.getStock() == 0) {
                producto.setDisponible(false);
            }

            productoRepository.save(producto);

            // retorno true indicando que el producto fue procesado
            return true;
        }

        // Si no hay stock suficiente, retornamos false
        return false;
    }

}
