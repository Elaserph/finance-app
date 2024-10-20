package com.finance.app.commons.path;

/**
 * Utility class for managing API paths related to currency exchange.
 * Provides constants for common paths and utility methods for generating specific paths.
 * This class is final and cannot be instantiated.
 *
 * @author
 * <a href="https://github.com/Elaserph">elaserph</a>
 */
public final class CurrencyExchangeApiPaths {

    public static final String ROOT_PATH = "/api/exchange-rate";
    public static final String EXCHANGE_RATE_PATH = "/{currencyFrom}/{currencyTo}";
    public static final String EXCHANGE_RATE_PATH_V1 = "/v1/{currencyFrom}/{currencyTo}";

    /**
     * Private constructor to prevent instantiation.
     */
    private CurrencyExchangeApiPaths() {
    }

    /**
     * Generates the API path for retrieving the exchange rate between two currencies.
     *
     * @param currencyFrom the currency to convert from, in ISO 4217 currency code format (e.g., "USD").
     * @param currencyTo the currency to convert to, in ISO 4217 currency code format (e.g., "EUR").
     * @param version the API version, e.g., "1", "2", pass null for the default version.
     * @return the full API path for retrieving the exchange rate.
     */
    public static String getExchangeRateApiPath(String currencyFrom, String currencyTo, String version) {
        return ROOT_PATH + (version != null ? "/v" + version : "") + EXCHANGE_RATE_PATH
                .replace("{currencyFrom}", currencyFrom)
                .replace("{currencyTo}", currencyTo);
    }
}
