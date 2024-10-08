package com.example.E_Commerce.infraestructura.services;

import com.example.E_Commerce.domain.entities.CategoriaEntity;
import com.example.E_Commerce.domain.repositories.CategoriaRepository;
import com.example.E_Commerce.infraestructura.exceptions.CategoriaNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public String agregarCategoria(String nombreCatgoria){
        CategoriaEntity categoria = CategoriaEntity.builder().nombre(nombreCatgoria).build();
        return categoriaRepository.save(categoria).getNombre();

    }

    public String modificarCategoria(Long id){
        CategoriaEntity categoria = categoriaRepository.findById(id).orElseThrow(()->new CategoriaNoEncontradoException("la categoria no se encontro"));
       return categoriaRepository.save(categoria).getNombre();
    }

    public void eliminarCategoria(Long id){
        if(!categoriaRepository.existsById(id))
            throw new CategoriaNoEncontradoException("la categoria no se encontro");

        categoriaRepository.deleteById(id);
    }

    public CategoriaEntity obtenerCategoriaPorId(Long id){
        return categoriaRepository.findById(id).orElseThrow( ()->new CategoriaNoEncontradoException("la categoria no se encontro"));
    }

    public List<String> mostrarCategorias(){
        return categoriaRepository.findAll().stream().map(CategoriaEntity::getNombre).toList();
    }
}
