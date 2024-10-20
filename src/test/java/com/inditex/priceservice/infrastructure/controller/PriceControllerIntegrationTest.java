package com.inditex.priceservice.infrastructure.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenFilterForNonApplicablePrice_whenGetApplicablePrice_thenAnswer404() throws Exception {
        var productId = 35455L;
        var brandId = 1;

        mockMvc.perform(get("/prices/applicable")
                        .param("applicationDate", "2020-06-13T21:00:00Z")
                        .param("productId", productId + "")
                        .param("brandId", brandId + ""))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("No price found for the given parameters"))
                .andExpect(jsonPath("$.path").value("/prices/applicable"));
    }

    @Test
    void test1_givenDateAt10AMOn14th_whenGetApplicablePrice_thenReturnPriceList1() throws Exception {
        var applicationDate = "2020-06-14T10:00:00Z";
        var productId = 35455L;
        var brandId = 1;

        mockMvc.perform(get("/prices/applicable")
                        .param("applicationDate", applicationDate)
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59Z"));
    }

    @Test
    void test2_givenDateAt4PMOn14th_whenGetApplicablePrice_thenReturnPriceList2() throws Exception {
        var applicationDate = "2020-06-14T16:00:00Z";
        var productId = 35455L;
        var brandId = 1;

        mockMvc.perform(get("/prices/applicable")
                        .param("applicationDate", applicationDate)
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00Z"));
    }

    @Test
    void test3_givenDateAt9PMOn14th_whenGetApplicablePrice_thenReturnPriceList1() throws Exception {
        var applicationDate = "2020-06-14T21:00:00Z";
        var productId = 35455L;
        var brandId = 1;

        mockMvc.perform(get("/prices/applicable")
                        .param("applicationDate", applicationDate)
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59Z"));
    }

    @Test
    void test4_givenDateAt10AMOn15th_whenGetApplicablePrice_thenReturnPriceList3() throws Exception {
        var applicationDate = "2020-06-15T10:00:00Z";
        var productId = 35455L;
        var brandId = 1;

        mockMvc.perform(get("/prices/applicable")
                        .param("applicationDate", applicationDate)
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T00:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2020-06-15T11:00:00Z"));
    }

    @Test
    void test5_givenDateAt9PMOn16th_whenGetApplicablePrice_thenReturnPriceList4() throws Exception {
        var applicationDate = "2020-06-16T21:00:00Z";
        var productId = 35455L;
        var brandId = 1;

        mockMvc.perform(get("/prices/applicable")
                        .param("applicationDate", applicationDate)
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T16:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59Z"));
    }

}