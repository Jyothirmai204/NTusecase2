package com.ems.exception;

import com.ems.dto.ResponseStructure;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ NOT FOUND
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseStructure<Object>> handleNotFound(
            ResourceNotFoundException ex, HttpServletRequest req) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseStructure<>(
                        "error",
                        ex.getMessage(),
                        null
                ));
    }

    // ✅ DUPLICATE
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ResponseStructure<Object>> handleDuplicate(
            DuplicateResourceException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseStructure<>(
                        "error",
                        ex.getMessage(),
                        null
                ));
    }

    // ✅ BAD REQUEST
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseStructure<Object>> handleBadRequest(
            BadRequestException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseStructure<>(
                        "error",
                        ex.getMessage(),
                        null
                ));
    }

    // ✅ UNAUTHORIZED
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseStructure<Object>> handleUnauthorized(
            UnauthorizedException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseStructure<>(
                        "error",
                        ex.getMessage(),
                        null
                ));
    }

    // ✅ FORBIDDEN
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseStructure<Object>> handleForbidden(
            ForbiddenException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseStructure<>(
                        "error",
                        ex.getMessage(),
                        null
                ));
    }

    // ✅ VALIDATION ERROR (@Valid)
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStructure<Object>> handleValidation(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {

        String msg = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseStructure<>(
                        "error",
                        msg,
                        null
                ));
    }

    // ✅ GENERIC ERROR (VERY IMPORTANT)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<Object>> handleGeneric(
            Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseStructure<>(
                        "error",
                        "Something went wrong",
                        null
                ));
    }
}