package com.example.E_Commerce.infraestructura.exceptions;

public class ProductoNoEncontradoException extends RuntimeException{
    public ProductoNoEncontradoException(String message){
        super(message);
    }
}
