package com.example.E_Commerce.infraestructura.exceptions;

public class ProductoSinStockException extends RuntimeException{
   public ProductoSinStockException (String message){super(message);
}
}