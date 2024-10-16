package com.finance.app.currencyexchange.controller;

import com.finance.app.currencyexchange.service.CurrencyExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(CurrencyExchangeController.class)
class CurrencyExchangeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyExchangeService testExchangeService;

    @Test
    void testGetExchangeRate_Success() throws Exception {
        //arrange
        BigDecimal rate = new BigDecimal("0.85");
        when(testExchangeService.getExchangeRate("USD", "EUR")).thenReturn(rate);
        //act,assert
        mockMvc.perform(get("/api/exchange-rate/USD/EUR"))
                .andExpect(status().isOk())
                .andExpect(content().string("0.85"));
    }

    @Test
    void testGetExchangeRate_NotFound() throws Exception {
        //arrange
        when(testExchangeService.getExchangeRate("ABC", "QWE")).thenReturn(null);
        //act,assert
        mockMvc.perform(get("/api/exchange-rate/USD/ABC"))
                .andExpect(status().isNotFound());
    }
}