package com.inditex.priceservice.domain.usecase;

import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.model.PriceFilter;

import java.util.Optional;

public interface PriceQuery {

    Optional<Price> getPriceForProductBrandAndDate(PriceFilter filter);

}
