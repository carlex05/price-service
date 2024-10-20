package com.inditex.priceservice.infrastructure.controller.exception;

public class NoPriceFoundException extends RuntimeException {
    public NoPriceFoundException(String message) {
        super(message);
    }
}
