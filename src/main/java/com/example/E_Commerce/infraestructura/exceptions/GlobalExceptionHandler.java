package com.example.E_Commerce.infraestructura.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessageException> duplicateEmailExceptionHandler(HttpServletRequest request, Exception exception ){
        return new ResponseEntity<ErrorMessageException>(new ErrorMessageException(exception.getMessage(),409), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessageException> illegalArgumentExceptionHanlder(HttpServletRequest request, Exception exception){
        return new ResponseEntity<ErrorMessageException>(new ErrorMessageException(exception.getMessage(),400),HttpStatus.BAD_REQUEST);
    }

    
}
