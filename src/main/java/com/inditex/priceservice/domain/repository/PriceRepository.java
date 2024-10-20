package com.inditex.priceservice.domain.repository;

import com.inditex.priceservice.domain.model.Price;

import java.util.Optional;

public interface PriceRepository {

    Optional<Price> findMostPriorityPriceForProductBrandAndDate(Long id);

}
