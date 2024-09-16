package com.emazon.msvc_stock.domain.exceptions;

public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException(String message){
        super(message);
    }
}
