package com.inditex.priceservice.application;

import com.inditex.priceservice.domain.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PriceQueryUseCaseTest {

    @Test
    void givenNoProductId_whenGetPriceForProductBrandAndDate_thenThrowsIllegalArgumentException(){
        var priceQueryUseCase = new PriceQueryUseCase(null);
        assertThrows(IllegalArgumentException.class, () -> priceQueryUseCase.getPriceForProductBrandAndDate(null));
    }

}