package com.finance.app.commons.path;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyExchangeApiPathsTest {

    @Test
    void getExchangeRateApiPath_Default() {
        //arrange,act,assert
        String expected = "/api/exchange-rate/USD/EUR";
        String actual = CurrencyExchangeApiPaths.getExchangeRateApiPath("USD", "EUR", null);
        assertEquals(expected, actual);
    }

    @Test
    void getExchangeRateApiPath_V1() {
        //arrange,act,assert
        String expected = "/api/exchange-rate/v1/USD/EUR";
        String actual = CurrencyExchangeApiPaths.getExchangeRateApiPath("USD", "EUR", "1");
        assertEquals(expected, actual);
    }
}