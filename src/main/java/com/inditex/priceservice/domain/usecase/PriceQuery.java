package com.inditex.priceservice.domain.usecase;

import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.model.PricesFilter;

import java.util.List;

public interface PriceQuery {

    List<Price> searchPricesByFilters(PricesFilter filter);

}
