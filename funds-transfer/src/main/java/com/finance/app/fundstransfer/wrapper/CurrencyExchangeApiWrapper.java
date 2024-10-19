package com.finance.app.fundstransfer.wrapper;

import com.finance.app.commons.path.CurrencyExchangeApiPaths;
import com.finance.app.fundstransfer.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class CurrencyExchangeApiWrapper {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${currency.exchange.server}")
    private String currencyExchangeServer;

    public BigDecimal getExchangeRate(String currencyFrom, String currencyTo) {
        try {
            String exchangeRateUrl = currencyExchangeServer + CurrencyExchangeApiPaths.getExchangeRateApiPath(currencyFrom, currencyTo, null);
            ResponseEntity<BigDecimal> response = restTemplate.getForEntity(exchangeRateUrl, BigDecimal.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResourceNotFoundException("Transfer failed! Exchange rate not found for " + currencyFrom + " to " + currencyTo);
            } else {
                throw ex;  // Re-throw other exceptions
            }
        }
    }
}
