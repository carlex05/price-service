package com.inditex.priceservice.infrastructure.controller;

import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.model.PricesFilter;
import com.inditex.priceservice.domain.usecase.PriceQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceQuery priceQuery;

    @Test
    void givenBadApplicationDate_whenGetPrices_thenAnswer400Code() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "invalid-date")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid value 'invalid-date' for parameter 'applicationDate'.")));
    }

    @Test
    void givenValidRequestForExistingPrice_whenGetPrices_thenAnswer200Code() throws Exception {
        Instant applicationDate = Instant.parse("2020-06-14T10:00:00Z");
        Long productId = 35455L;
        Integer brandId = 1;
        PricesFilter filter = new PricesFilter(applicationDate, productId, brandId);
        Price price = new Price(
                brandId,
                applicationDate,
                applicationDate.plusSeconds(3600),
                1L,
                productId,
                0,
                new BigDecimal("35.50"),
                "EUR"
        );

        when(priceQuery.getPriceForProductBrandAndDate(filter)).thenReturn(Optional.of(price));
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T10:00:00Z")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(productId.intValue()))
                .andExpect(jsonPath("$.brandId").value(brandId))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void givenValidRequestForNonExistingPrice_whenGetPrices_thenAnswer404Code() throws Exception {
        Instant applicationDate = Instant.parse("2020-06-14T21:00:00Z");
        Long productId = 35455L;
        Integer brandId = 1;
        PricesFilter filter = new PricesFilter(applicationDate, productId, brandId);

        when(priceQuery.getPriceForProductBrandAndDate(filter)).thenReturn(Optional.empty());

        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T21:00:00Z")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString()))
                .andExpect(status().isNotFound());
    }

}