package com.emazon.msvc_stock.domain.exception;

public class DuplicateCategoryNameException extends RuntimeException {
    public DuplicateCategoryNameException(String message){
        super(message);
    }
}
