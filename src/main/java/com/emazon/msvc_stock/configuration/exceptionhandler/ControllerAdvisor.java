package com.emazon.msvc_stock.configuration.exceptionhandler;

import com.emazon.msvc_stock.domain.exception.DuplicateNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateCategoryNameException(DuplicateNameException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.CONFLICT.toString(), LocalDateTime.now()
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(ex.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
