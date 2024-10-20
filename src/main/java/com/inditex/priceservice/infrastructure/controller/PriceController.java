package com.inditex.priceservice.infrastructure.controller;

import com.inditex.priceservice.domain.model.PriceFilter;
import com.inditex.priceservice.domain.usecase.PriceQuery;
import com.inditex.priceservice.infrastructure.generated.api.DefaultApi;
import com.inditex.priceservice.infrastructure.generated.model.PriceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 *
 * @author cgomezr
 */
@RestController
@RequiredArgsConstructor
public class PriceController implements DefaultApi {

    private final PriceQuery priceQuery;

    @Override
    public ResponseEntity<PriceResponseDto> getPrices(Instant applicationDate, Long productId, Integer brandId) {
        var applicablePrice = priceQuery.getPriceForProductBrandAndDate(new PriceFilter(applicationDate, productId, brandId));
        return applicablePrice.map(price -> ResponseEntity.ok(new PriceResponseDto(
                price.productId(),
                price.brandId(),
                price.priceList(),
                price.priority(),
                price.startDate(),
                price.endDate(),
                price.price(),
                price.currency()
        ))).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
