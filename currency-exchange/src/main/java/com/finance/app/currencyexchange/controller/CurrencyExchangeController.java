package com.finance.app.currencyexchange.controller;

import com.finance.app.commons.path.CurrencyExchangeApiPaths;
import com.finance.app.currencyexchange.service.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(CurrencyExchangeApiPaths.ROOT_PATH)
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @GetMapping(path = {
            CurrencyExchangeApiPaths.EXCHANGE_RATE_PATH,
            CurrencyExchangeApiPaths.EXCHANGE_RATE_PATH_V1
    })
    public ResponseEntity<BigDecimal> getExchangeRate(@PathVariable String currencyFrom, @PathVariable String currencyTo) {
        var rate = currencyExchangeService.getExchangeRate(currencyFrom, currencyTo);
        return rate != null ? ResponseEntity.ok(rate): ResponseEntity.notFound().build();
    }
}
