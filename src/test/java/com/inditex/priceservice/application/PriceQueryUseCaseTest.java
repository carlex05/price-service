package com.inditex.priceservice.application;

import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.model.PriceFilter;
import com.inditex.priceservice.domain.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PriceQueryUseCaseTest {

    @Test
    void givenNoProductId_whenGetPriceForProductBrandAndDate_thenThrowsIllegalArgumentException() {
        var priceQueryUseCase = new PriceQueryUseCase(null);
        assertThrows(IllegalArgumentException.class, () -> priceQueryUseCase.getPriceForProductBrandAndDate(new PriceFilter(
                Instant.now(),
                null,
                1
        )));
    }

    @Test
    void givenNoApplicationDate_whenGetPriceForProductBrandAndDate_thenThrowsIllegalArgumentException() {
        var priceQueryUseCase = new PriceQueryUseCase(null);
        assertThrows(IllegalArgumentException.class, () -> priceQueryUseCase.getPriceForProductBrandAndDate(new PriceFilter(
                null,
                1L,
                1
        )));
    }

    @Test
    void givenNoBrandId_whenGetPriceForProductBrandAndDate_thenThrowsIllegalArgumentException() {
        var priceQueryUseCase = new PriceQueryUseCase(null);
        assertThrows(IllegalArgumentException.class, () -> priceQueryUseCase.getPriceForProductBrandAndDate(new PriceFilter(
                Instant.now(),
                1L,
                null
        )));
    }

    @Test
    void givenNullFilter_whenGetPriceForProductBrandAndDate_thenThrowsIllegalArgumentException() {
        var priceQueryUseCase = new PriceQueryUseCase(null);
        assertThrows(IllegalArgumentException.class, () -> priceQueryUseCase.getPriceForProductBrandAndDate(null));
    }

    @Test
    void givenFilterForPriceNotFound_whenGetPriceForProductBrandAndDate_thenReturnEmptyOptional() {
        var repository = Mockito.mock(PriceRepository.class);
        var priceQueryUseCase = new PriceQueryUseCase(repository);
        var filter = new PriceFilter(
                Instant.now(),
                1L,
                1
        );
        Mockito.when(repository.findTopPriorityByProductBrandAndDate(filter))
                .thenReturn(Optional.empty());
        var actual = priceQueryUseCase.getPriceForProductBrandAndDate(filter);
        assertTrue(actual.isEmpty());
    }

    @Test
    void givenFilterForPriceFound_whenGetPriceForProductBrandAndDate_thenReturnPrice() {
        var repository = Mockito.mock(PriceRepository.class);
        var priceQueryUseCase = new PriceQueryUseCase(repository);
        var price = new Price(
                1,
                Instant.now(),
                Instant.now().plusSeconds(3600L),
                2L,
                1L,
                1,
                BigDecimal.valueOf(20.5),
                "EUR"
        );
        var filter = new PriceFilter(
                Instant.now(),
                1L,
                1
        );
        Mockito.when(repository.findTopPriorityByProductBrandAndDate(filter)).thenReturn(Optional.of(price));
        var actual = priceQueryUseCase.getPriceForProductBrandAndDate(filter);
        assertTrue(actual.isPresent());
        assertEquals(price.price(), actual.get().price());
        assertEquals(price.priceList(), actual.get().priceList());
    }
}