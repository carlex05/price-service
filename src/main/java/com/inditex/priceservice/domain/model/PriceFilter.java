package com.inditex.priceservice.domain.model;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record PriceFilter(
        @NotNull Instant applicationAt,
        @NotNull Long productId,
        @NotNull Integer brandId
) {
}
