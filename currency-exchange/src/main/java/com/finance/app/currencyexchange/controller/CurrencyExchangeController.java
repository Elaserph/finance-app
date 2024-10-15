package com.finance.app.currencyexchange.controller;

import com.finance.app.currencyexchange.service.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/exchange-rate")
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @GetMapping(path = {
            "/{currencyFrom}/{currencyTo}",
            "/v1/{currencyFrom}/{currencyTo}"
    })
    public ResponseEntity<BigDecimal> getExchangeRate(@PathVariable String currencyFrom, @PathVariable String currencyTo) {
        var rate = currencyExchangeService.getExchangeRate(currencyFrom, currencyTo);
        return ResponseEntity.ok(rate);
    }
}
