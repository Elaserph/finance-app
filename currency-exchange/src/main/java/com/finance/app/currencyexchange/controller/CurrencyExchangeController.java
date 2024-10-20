package com.finance.app.currencyexchange.controller;

import com.finance.app.commons.path.CurrencyExchangeApiPaths;
import com.finance.app.currencyexchange.service.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * REST Controller for handling currency exchange rate requests.
 *
 * @author <a href="https://github.com/Elaserph">elaserph</a>
 */
@RestController
@RequestMapping(CurrencyExchangeApiPaths.ROOT_PATH)
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    /**
     * Retrieves the exchange rate between two specified currencies.
     *
     * @param currencyFrom the currency to convert from,  in ISO 4217 currency code format (e.g., "USD").
     * @param currencyTo   the currency to convert to,  in ISO 4217 currency code format (e.g., "EUR").
     * @return a {@link ResponseEntity} containing the exchange rate, or a 404 status if the exchange rate is not found.
     */
    @GetMapping(path = {
            CurrencyExchangeApiPaths.EXCHANGE_RATE_PATH,
            CurrencyExchangeApiPaths.EXCHANGE_RATE_PATH_V1
    })
    public ResponseEntity<BigDecimal> getExchangeRate(@PathVariable String currencyFrom, @PathVariable String currencyTo) {
        var rate = currencyExchangeService.getExchangeRate(currencyFrom, currencyTo);
        return rate != null ? ResponseEntity.ok(rate) : ResponseEntity.notFound().build();
    }
}
