package com.finance.app.fundstransfer.controller;

import com.finance.app.commons.path.FundsTransferApiPaths;
import com.finance.app.fundstransfer.FundsTransferApplicationTests;
import com.finance.app.fundstransfer.wrapper.CurrencyExchangeApiWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerIntegrationTest extends FundsTransferApplicationTests {

    @MockBean
    private CurrencyExchangeApiWrapper currencyExchangeApiWrapper;

    @BeforeEach
    public void setUp() {
        //set external api mock to return exchange rate for testing
        when(currencyExchangeApiWrapper.getExchangeRate("INR", "USD")).thenReturn(new BigDecimal("0.012"));
    }

    @Test
    void transferFunds_Success() throws Exception {
        String requestJson = """
                {
                    "ownerId": 20,
                    "senderAccount": "ACC2040",
                    "receiverAccount": "ACC1937",
                    "transferAmount": 10,
                    "transferAccountCurrency": "INR"
                }""";

        mockMvc.perform(post(FundsTransferApiPaths.getFundsTransferApiPath(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Success")));
    }

    @Test
    void transferFunds_InsufficientFunds() throws Exception {
        String requestJson = """
                {
                    "ownerId": 20,
                    "senderAccount": "ACC2040",
                    "receiverAccount": "ACC1937",
                    "transferAmount": 10000,
                    "transferAccountCurrency": "INR"
                }""";

        mockMvc.perform(post(FundsTransferApiPaths.getFundsTransferApiPath(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Insufficient Funds")));
    }

    private static Stream<String> jsonRequests_ResourceNotFound() {
        return Stream.of(
                //receiver account not found
                """
                {
                    "ownerId": 20,
                    "senderAccount": "ACC2040",
                    "receiverAccount": "ACC1937111",
                    "transferAmount": 10,
                    "transferAccountCurrency": "INR"
                }""",
                //sender account not found
                """
                {
                    "ownerId": 20,
                    "senderAccount": "ACC20401111",
                    "receiverAccount": "ACC1937",
                    "transferAmount": 10,
                    "transferAccountCurrency": "INR"
                }"""
        );
    }

    @ParameterizedTest
    @MethodSource("jsonRequests_ResourceNotFound")
    void transferFunds_ResourceNotFound(String requestJson) throws Exception {
        mockMvc.perform(post(FundsTransferApiPaths.getFundsTransferApiPath(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Resource not found")));
    }


    private static Stream<String> jsonRequests_MethodArgumentNotValid() {
        return Stream.of(
                //transfer amount must not be negative
                """
                {
                    "ownerId": 20,
                    "senderAccount": "ACC2040",
                    "receiverAccount": "ACC1937",
                    "transferAmount": -1000,
                    "transferAccountCurrency": "INR"
                }""",
                //accounts must not be blank/empty/null
                """
                {
                    "ownerId": 20,
                    "senderAccount": " ",
                    "receiverAccount": "",
                    "transferAmount": 10,
                    "transferAccountCurrency": "INR"
                }""",
                //OwnerId must not be null
                """
                {
                    "ownerId": null,
                    "senderAccount": "ACC2040",
                    "receiverAccount": "ACC1937",
                    "transferAmount": 10,
                    "transferAccountCurrency": "INR"
                }""",
                //transferAccountCurrency must not be blank/empty/null
                """
                {
                    "ownerId": 20,
                    "senderAccount": "ACC2040",
                    "receiverAccount": "ACC1937",
                    "transferAmount": 10,
                    "transferAccountCurrency": ""
                }"""
        );
    }

    @ParameterizedTest
    @MethodSource("jsonRequests_MethodArgumentNotValid")
    void transferFunds_MethodArgumentNotValid(String requestJson) throws Exception {
        mockMvc.perform(post(FundsTransferApiPaths.getFundsTransferApiPath(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Arguments validation error")));
    }

    @Test
    void transferFunds_ExternalApiError() throws Exception {
        //reset the mock to return server error - handled by RuntimeException ControllerAdvice
        when(currencyExchangeApiWrapper.getExchangeRate("INR", "USD"))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        String requestJson = """
                {
                    "ownerId": 20,
                    "senderAccount": "ACC2040",
                    "receiverAccount": "ACC1937",
                    "transferAmount": 100,
                    "transferAccountCurrency": "INR"
                }""";

        mockMvc.perform(post(FundsTransferApiPaths.getFundsTransferApiPath(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Unknown error")));
    }

    private static Stream<String> jsonRequests_IllegalArgument() {
        return Stream.of(
                //transferAccountCurrency mismatch, USD requested but the sender account currency is INR, must be same
                //usually transferAccountCurrency will be filled by system/client-ui, based on the logged-in/selected sender account's details
                """
                {
                    "ownerId": 20,
                    "senderAccount": "ACC2040",
                    "receiverAccount": "ACC2040",
                    "transferAmount": 10,
                    "transferAccountCurrency": "USD"
                }""",
                //sender and receiver accounts must not be same
                """
                {
                    "ownerId": 20,
                    "senderAccount": "ACC2040",
                    "receiverAccount": "ACC2040",
                    "transferAmount": 10,
                    "transferAccountCurrency": "INR"
                }"""
        );
    }

    @ParameterizedTest
    @MethodSource("jsonRequests_IllegalArgument")
    void transferFunds_IllegalArgument(String requestJson) throws Exception {
        mockMvc.perform(post(FundsTransferApiPaths.getFundsTransferApiPath(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Illegal arguments")));
    }
}