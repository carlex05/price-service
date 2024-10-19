package com.inditex.priceservice.infrastructure.controller;

import com.inditex.priceservice.infrastructure.generated.api.DefaultApi;
import com.inditex.priceservice.infrastructure.generated.model.PriceResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 *
 * @author cgomezr
 */
@RestController
public class PriceController implements DefaultApi {


    @Override
    public ResponseEntity<PriceResponseDto> getPrices(Instant applicationDate, Long productId, Integer brandId) {
        return null;
    }
}
