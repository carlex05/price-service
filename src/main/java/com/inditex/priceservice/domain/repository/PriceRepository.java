package com.inditex.priceservice.domain.repository;

import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.model.PriceFilter;

import java.util.Optional;

public interface PriceRepository {

    Optional<Price> findTopPriorityByProductBrandAndDate(PriceFilter filter);

}
