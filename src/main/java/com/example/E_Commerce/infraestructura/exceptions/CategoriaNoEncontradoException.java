package com.example.E_Commerce.infraestructura.exceptions;

public class CategoriaNoEncontradoException extends RuntimeException{
    public CategoriaNoEncontradoException(String message){
        super(message);
    }
}
