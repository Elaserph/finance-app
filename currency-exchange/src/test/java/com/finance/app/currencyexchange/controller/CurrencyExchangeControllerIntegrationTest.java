package com.finance.app.currencyexchange.controller;

import com.finance.app.commons.path.CurrencyExchangeApiPaths;
import com.finance.app.currencyexchange.CurrencyExchangeApplicationTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CurrencyExchangeControllerIntegrationTest extends CurrencyExchangeApplicationTests {

    @ParameterizedTest
    @CsvSource({
            "USD, EUR, 0.85",
            "GbP, USd, 1.33",
            "eur, inr, 91.54",
            "USd, NOK, 8.75",
            "USd, usd, 1"
    })
    void testGetExchangeRate_Success(String fromCurrency, String toCurrency, String expectedRate) throws Exception {
        mockMvc.perform(get(CurrencyExchangeApiPaths.getExchangeRateApiPath(fromCurrency, toCurrency, null)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedRate));
    }

    @Test
    void testGetExchangeRate_NotFound() throws Exception {
        mockMvc.perform(get(CurrencyExchangeApiPaths.getExchangeRateApiPath("USD", "ABC", null)))
                .andExpect(status().isNotFound());
    }
}