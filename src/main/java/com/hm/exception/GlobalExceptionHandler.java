package com.hm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/*
	 * @ExceptionHandler(CustomException.class) public ResponseEntity<Map<String,
	 * String>> handleCustomException(CustomException ex) { return
	 * ResponseEntity.status(ex.getStatus()) .body(Map.of("code", ex.getCode(),
	 * "message", ex.getMessage())); }
	 */

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleCustomException(CustomException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("code", ex.getCode());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, ex.getStatus());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> response = Map.of(
                "code", "VALIDATION_ERROR",
                "message", "Invalid input",
                "errors", errors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        return new ResponseEntity<>(Map.of("code", "SERVER_ERROR", "message", "An error occurred: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
