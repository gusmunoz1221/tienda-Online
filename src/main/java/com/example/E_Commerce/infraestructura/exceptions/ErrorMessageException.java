package com.example.E_Commerce.infraestructura.exceptions;

import lombok.Data;

@Data
public class ErrorMessageException {
    private String message;
    private int code;

    public ErrorMessageException(String message, int code) {
        this.message=message;
        this.code=code;
    }
}
