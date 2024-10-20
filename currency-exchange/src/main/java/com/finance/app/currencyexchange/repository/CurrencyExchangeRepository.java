package com.finance.app.currencyexchange.repository;

import com.finance.app.currencyexchange.entity.CurrencyExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

/**
 * Repository for handling currency exchange rate requests.
 *
 * @author
 * <a href="https://github.com/Elaserph">elaserph</a>
 */
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchangeEntity, Long> {

    /**
     * Fires JPQL to find the exchange rate between two specified currencies.
     *
     *  @param currencyFrom the currency to convert from, in ISO 4217 currency code format (e.g., "USD").
     *  @param currencyTo the currency to convert to, in ISO 4217 currency code format (e.g., "EUR").
     *  @return the exchange rate between the specified currencies, or null if no exchange rate is found.
     */
    @Query("SELECT e.rate FROM CurrencyExchangeEntity e WHERE e.currencyFrom = :currencyFrom AND e.currencyTo = :currencyTo")
    BigDecimal findRateByCurrencyFromAndCurrencyTo(@Param("currencyFrom") String currencyFrom, @Param("currencyTo") String currencyTo);

}
