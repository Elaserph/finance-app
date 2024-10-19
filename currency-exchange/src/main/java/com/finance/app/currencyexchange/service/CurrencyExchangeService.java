package com.finance.app.currencyexchange.service;

import com.finance.app.currencyexchange.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyExchangeService {

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    public BigDecimal getExchangeRate(String currencyFrom, String currencyTo) {
        return currencyExchangeRepository.findRateByCurrencyFromAndCurrencyTo(currencyFrom.toUpperCase(), currencyTo.toUpperCase());
    }
}
