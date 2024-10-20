package com.inditex.priceservice.application;

import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.model.PriceFilter;
import com.inditex.priceservice.domain.repository.PriceRepository;
import com.inditex.priceservice.domain.usecase.PriceQuery;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PriceQueryUseCase implements PriceQuery {

    private final PriceRepository priceRepository;

    @Override
    public Optional<Price> getPriceForProductBrandAndDate(PriceFilter filter) {
        throw new IllegalArgumentException();
    }
}
