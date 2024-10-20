package com.inditex.priceservice.infrastructure.controller;

import com.inditex.priceservice.infrastructure.controller.exception.NoPriceFoundException;
import com.inditex.priceservice.infrastructure.generated.model.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Invalid value '%s' for parameter '%s'.", ex.getValue(), ex.getName());
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(NoPriceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoPriceFoundException(
            NoPriceFoundException ex, HttpServletRequest request) {

        var errorResponse = new ErrorResponseDto();
        errorResponse.setTimestamp(Instant.now());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
