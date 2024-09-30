package com.example.E_Commerce.infraestructura.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CorreoDuplicadolException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<MensajeDeErrorException> handlerduplicateEmail(HttpServletRequest request, Exception exception ){
        return new ResponseEntity<MensajeDeErrorException>(new MensajeDeErrorException(exception.getMessage(),409), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ArgumentoIlegalException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MensajeDeErrorException> handlerillegalArgument(HttpServletRequest request, Exception exception){
        return new ResponseEntity<MensajeDeErrorException>(new MensajeDeErrorException(exception.getMessage(),400),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<MensajeDeErrorException> handlerClientNotFound(HttpServletRequest request, Exception exception){
        return new ResponseEntity<MensajeDeErrorException>(new MensajeDeErrorException(exception.getMessage(),404),HttpStatus.NOT_FOUND);
    }
}
