package com.inditex.priceservice.domain.model;

import java.time.Instant;

public record PriceFilter(
        Instant applicationAt,
        Long productId,
        Integer brandId
) {
}
