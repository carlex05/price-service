package com.inditex.priceservice.infrastructure.repository;

import com.inditex.priceservice.domain.model.PriceFilter;
import com.inditex.priceservice.domain.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(PriceRepositoryImpl.class)
class PriceRepositoryImplTest {

    @Autowired
    PriceRepository repository;

    @Test
    void givenFilterForOneApplicablePrice_whenFindTopPriorityByProductBrandAndDate_thenReturnPriceList1() {
        var applicationDate = Instant.parse("2020-06-14T10:00:00Z");
        var productId = 35455L;
        var brandId = 1;

        var priceEntityOptional = repository.findTopPriorityByProductBrandAndDate(new PriceFilter(
                applicationDate,
                productId,
                brandId
        ));

        assertTrue(priceEntityOptional.isPresent());
        var priceEntity = priceEntityOptional.get();
        assertEquals(1L, priceEntity.priceList());
        assertEquals(0, priceEntity.priority());
        assertEquals(new BigDecimal("35.50"), priceEntity.price());
        assertTrue(priceEntityOptional.get().startDate().compareTo(applicationDate) <= 0
                && priceEntityOptional.get().endDate().compareTo(applicationDate) >= 0);
    }

    @Test
    void givenFilterForTwoApplicablePrices_whenFindTopPriorityByProductBrandAndDate_thenReturnPriceList1() {
        var applicationDate = Instant.parse("2020-06-15T10:00:00Z");
        var productId = 35455L;
        var brandId = 1;

        var priceEntityOptional = repository.findTopPriorityByProductBrandAndDate(new PriceFilter(
                applicationDate,
                productId,
                brandId
        ));

        assertTrue(priceEntityOptional.isPresent());
        var priceEntity = priceEntityOptional.get();
        assertEquals(1, priceEntity.priority());
        assertEquals(3L, priceEntity.priceList());
        assertEquals(new BigDecimal("30.50"), priceEntity.price());
        assertTrue(priceEntityOptional.get().startDate().compareTo(applicationDate) <= 0
                && priceEntityOptional.get().endDate().compareTo(applicationDate) >= 0);
    }

    @Test
    void givenFilterForNonApplicablePrices_whenFindTopPriorityByProductBrandAndDate_thenReturnPriceList1() {
        var applicationDate = Instant.parse("2020-06-13T10:00:00Z");
        var productId = 35455L;
        var brandId = 1;

        var priceEntityOptional = repository.findTopPriorityByProductBrandAndDate(new PriceFilter(
                applicationDate,
                productId,
                brandId
        ));

        assertTrue(priceEntityOptional.isEmpty());

    }

}