package com.finance.app.currencyexchange.repository;

import com.finance.app.currencyexchange.entity.CurrencyExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchangeEntity, Long> {

    @Query("SELECT e.rate FROM CurrencyExchangeEntity e WHERE e.currencyFrom = :currencyFrom AND e.currencyTo = :currencyTo")
    BigDecimal findRateByCurrencyFromAndCurrencyTo(@Param("currencyFrom") String currencyFrom, @Param("currencyTo") String currencyTo);

}
