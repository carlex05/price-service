package com.inditex.priceservice.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Price(
        Integer brandId,
        Instant startDate,
        Instant endDate,
        Long priceList,
        Long productId,
        Integer priority,
        BigDecimal price,
        String currency
        ) {


}
