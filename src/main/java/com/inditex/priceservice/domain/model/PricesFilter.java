package com.inditex.priceservice.domain.model;

import java.time.Instant;

public record PricesFilter(
        Instant applicationAt,
        Long productId,
        Integer brandId
) {
}
