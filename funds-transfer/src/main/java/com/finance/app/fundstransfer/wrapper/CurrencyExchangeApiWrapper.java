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

/**
 * Wrapper class for calling the external currency-exchange API.
 * Provides methods to retrieve exchange rates between currencies.
 *
 * <p>
 * The class uses {@link RestTemplate} for making HTTP requests to the API.
 * </p>
 * <p>
 * The API server URL is injected from the application properties using the {@link Value} annotation.
 * </p>
 *
 * @author
 * <a href="https://github.com/Elaserph">elaserph</a>
 */
@Component
public class CurrencyExchangeApiWrapper {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${currency.exchange.server}")
    private String currencyExchangeServer;

    /**
     * Retrieves the exchange rate between two currencies.
     *
     * @param currencyFrom the currency to convert from, currency code format (e.g., "USD").
     * @param currencyTo the currency to convert to, currency code format (e.g., "EUR").
     * @return the exchange rate between the specified currencies.
     * @throws ResourceNotFoundException if the exchange rate is not found.
     * @throws HttpClientErrorException for other HTTP 4xx errors.
     */
    public BigDecimal getExchangeRate(String currencyFrom, String currencyTo) {
        try {
            //get the exchange rate url
            String exchangeRateUrl = currencyExchangeServer + CurrencyExchangeApiPaths.getExchangeRateApiPath(currencyFrom, currencyTo, null);
            //rest get call
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
