package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.api.DTOs.request.producto.ProductoSolicitudDTO;
import com.example.E_Commerce.api.DTOs.response.producto.ProductoRespuestaDTO;
import com.example.E_Commerce.domain.entities.CategoriaEntity;
import com.example.E_Commerce.domain.entities.ProductoEntity;
import com.example.E_Commerce.domain.repositories.ProductoRepository;
import com.example.E_Commerce.infraestructura.exceptions.ArgumentoIlegalException;
import com.example.E_Commerce.infraestructura.exceptions.ProductoNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;

    public ProductoService(ProductoRepository productoRepository, CategoriaService categoriaService) {
        this.productoRepository = productoRepository;
        this.categoriaService = categoriaService;
    }
    

    public ProductoRespuestaDTO agregarProducto(ProductoSolicitudDTO productoSolicitud){

      CategoriaEntity categoria = categoriaService.obtenerCategoriaPorId(productoSolicitud.getCategoriaId());

      if(productoRepository.existsByNombre(productoSolicitud.getNombre()))
          throw new ArgumentoIlegalException("el nombre del producto ya existe");


      ProductoEntity producto = ProductoEntity.builder()
             .nombre(productoSolicitud.getNombre())
             .descripcion(productoSolicitud.getDescripcion())
             .precio(productoSolicitud.getPrecio())
             .stock(productoSolicitud.getStock())
             .disponible(true)
             .categoria(categoria).build();

     productoRepository.save(producto);

    return ProductoRespuestaDTO.builder().nombre(producto.getNombre())
             .descripcion(producto.getDescripcion())
             .precio(producto.getPrecio())
             .stock(producto.getStock())
             .categoria(producto.getCategoria()).build();
    }
    public ProductoEntity obtenerProductoPorId(Long id){
        return productoRepository.findById(id)
                .filter(ProductoEntity::getDisponible)
                .orElseThrow(() -> new ProductoNoEncontradoException("El producto seleccionado no se encuentra disponible"));
    }

    public ProductoRespuestaDTO obtenerProductoDto(Long id){
        return productoRepository.findById(id)
                .filter(ProductoEntity::getDisponible)
                .map(producto -> new ProductoRespuestaDTO(
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getPrecio(),
                        producto.getStock(),
                        producto.getCategoria()
                ))
                .orElseThrow(() -> new ProductoNoEncontradoException("El producto seleccionado no se encuentra disponible"));
    }


    public List<ProductoRespuestaDTO> obtenerProductos(){
        return productoRepository.findAll()
                .stream()
                .filter(ProductoEntity::getDisponible)
                .map(producto -> new ProductoRespuestaDTO(
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getPrecio(),
                        producto.getStock(),
                        producto.getCategoria()
                )).toList();
    }

    public ProductoRespuestaDTO modificarProducto(Long id, ProductoSolicitudDTO productoDto){
        if(!productoRepository.existsById(id))
            throw new ProductoNoEncontradoException("El producto seleccionado no se encuentra disponible");

        ProductoEntity producto = obtenerProductoPorId(id);
        producto.setNombre(productoDto.getNombre());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setStock(productoDto.getStock());
        producto.setPrecio(productoDto.getPrecio());
        producto.setCategoria(categoriaService.obtenerCategoriaPorId(productoDto.getCategoriaId()));

        productoRepository.save(producto);

        return ProductoRespuestaDTO.builder().nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .categoria(producto.getCategoria()).build();
    }

    public void eliminarProducto(Long id){

        if(!productoRepository.existsById(id))
            throw new ProductoNoEncontradoException("el producto seleccionado no se encuentra disponible");

        productoRepository.deleteById(id);
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
