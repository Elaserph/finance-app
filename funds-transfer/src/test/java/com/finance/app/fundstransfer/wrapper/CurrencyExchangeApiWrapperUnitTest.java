package com.finance.app.fundstransfer.wrapper;

import com.finance.app.fundstransfer.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class CurrencyExchangeApiWrapperUnitTest {

    @Mock
    private RestTemplate restTemplateTest;

    @InjectMocks
    private CurrencyExchangeApiWrapper currencyExchangeApiWrapperTest;

    private static final String EXCHANGE_RATE_TEST_URL = "http://localhost:8080/api/exchange-rate/USD/EUR";

    @BeforeEach
    public void setUp() {
        // Inject the base URL via reflection
        setField(currencyExchangeApiWrapperTest, "currencyExchangeServer", "http://localhost:8080");
    }

        @Test
        void testGetExchangeRate_Success() {
        BigDecimal expectedRate = BigDecimal.valueOf(0.85);
        when(restTemplateTest.getForEntity(EXCHANGE_RATE_TEST_URL, BigDecimal.class))
                .thenReturn(new ResponseEntity<>(expectedRate, HttpStatus.OK));

        BigDecimal actualRate = currencyExchangeApiWrapperTest.getExchangeRate("USD", "EUR");
        assertEquals(expectedRate, actualRate);
    }

        @Test
        void testGetExchangeRate_NotFound() {
        when(restTemplateTest.getForEntity(EXCHANGE_RATE_TEST_URL, BigDecimal.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(ResourceNotFoundException.class, () ->
                currencyExchangeApiWrapperTest.getExchangeRate("USD", "EUR"));
    }

        @Test
        void testGetExchangeRate_OtherException() {
        when(restTemplateTest.getForEntity(EXCHANGE_RATE_TEST_URL, BigDecimal.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(HttpClientErrorException.class, () ->
                currencyExchangeApiWrapperTest.getExchangeRate("USD", "EUR"));
    }
}