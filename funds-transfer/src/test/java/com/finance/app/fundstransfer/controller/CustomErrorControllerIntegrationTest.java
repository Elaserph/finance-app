package com.finance.app.fundstransfer.controller;

import com.finance.app.commons.path.CurrencyExchangeApiPaths;
import com.finance.app.fundstransfer.FundsTransferApplicationTests;
import jakarta.servlet.RequestDispatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomErrorControllerIntegrationTest extends FundsTransferApplicationTests {


    @ParameterizedTest
    @CsvSource({
            CurrencyExchangeApiPaths.ROOT_PATH,
            "/api",
            "/randomUrl",
            "/api/random"
    })
    void handleError_RandomUrlCall(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    void handleError_404() throws Exception {
        mockMvc.perform(get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 404))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("status code: 404")));
    }

    @Test
    void handleError_500() throws Exception {
        mockMvc.perform(get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 500))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("status code: 500")));
    }

    @Test
    void handleError_Unknown() throws Exception {
        mockMvc.perform(get("/error"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Unknown error")));
    }
}