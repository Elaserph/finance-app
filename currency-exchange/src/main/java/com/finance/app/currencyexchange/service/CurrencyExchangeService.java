package com.finance.app.currencyexchange.service;

import com.finance.app.currencyexchange.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service for handling currency exchange rate requests.
 *
 * @author
 * <a href="https://github.com/Elaserph">elaserph</a>
 */
@Service
public class CurrencyExchangeService {

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    /**
     * Retrieves the exchange rate between two currencies.
     *
     * @param currencyFrom the currency to convert from, currency code format (e.g., "USD").
     * @param currencyTo the currency to convert to, currency code format (e.g., "EUR").
     * @return the exchange rate between the specified currencies, or null if no exchange rate is found.
     */
    public BigDecimal getExchangeRate(String currencyFrom, String currencyTo) {
        return currencyExchangeRepository.findRateByCurrencyFromAndCurrencyTo(currencyFrom.toUpperCase(), currencyTo.toUpperCase());
    }
}
