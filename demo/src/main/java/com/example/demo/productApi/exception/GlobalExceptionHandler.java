package com.example.demo.productApi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)// for enum validations
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            ValidationException ex) {
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoProductFoundException(Exception e) {
        return new ResponseEntity<>(Map.of("Message", e.getMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SpFailedException.class)
    public ResponseEntity<Map<String, String>> handleSpFailedException(Exception e) {
        return new ResponseEntity<>(Map.of("Message", e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
