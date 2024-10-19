package com.finance.app.commons.path;

public final class CurrencyExchangeApiPaths {

    public static final String ROOT_PATH = "/api/exchange-rate";
    public static final String EXCHANGE_RATE_PATH = "/{currencyFrom}/{currencyTo}";
    public static final String EXCHANGE_RATE_PATH_V1 = "/v1/{currencyFrom}/{currencyTo}";
    private CurrencyExchangeApiPaths() {
    }

    public static String getExchangeRateApiPath(String currencyFrom, String currencyTo, String version) {
        return ROOT_PATH + (version != null ? "/v" + version : "") + EXCHANGE_RATE_PATH
                .replace("{currencyFrom}", currencyFrom)
                .replace("{currencyTo}", currencyTo);
    }
}
