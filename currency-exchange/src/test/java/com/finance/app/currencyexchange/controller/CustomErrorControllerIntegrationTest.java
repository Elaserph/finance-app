package com.finance.app.currencyexchange.controller;

import com.finance.app.currencyexchange.CurrencyExchangeApplicationTests;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomErrorControllerIntegrationTest extends CurrencyExchangeApplicationTests {

    @Test
    void handleError() throws Exception {
        mockMvc.perform(get("/api/exchange-rate/"))
                .andExpect(status().isNotFound());
    }
}