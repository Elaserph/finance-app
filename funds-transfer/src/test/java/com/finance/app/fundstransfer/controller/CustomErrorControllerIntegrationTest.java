package com.finance.app.fundstransfer.controller;

import com.finance.app.commons.path.CurrencyExchangeApiPaths;
import com.finance.app.fundstransfer.FundsTransferApplicationTests;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomErrorControllerIntegrationTest extends FundsTransferApplicationTests {

    @Test
    void handleError() throws Exception {
        mockMvc.perform(get(CurrencyExchangeApiPaths.ROOT_PATH))
                .andExpect(status().isNotFound());
    }
}