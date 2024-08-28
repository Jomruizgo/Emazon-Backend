package com.emazon.msvc_stock.domain.exception;

public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException(String message){
        super(message);
    }
}
