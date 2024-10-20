package com.finance.app.currencyexchange.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing the currency exchange information.
 * Maps to the "currency_exchange" table in the database.
 * Provides details about the exchange rate between two currencies at a specific timestamp.
 * For representational purpose, data is static
 *
 * @author
 * <a href="https://github.com/Elaserph">elaserph</a>
 */
@Entity
@Table(name = "currency_exchange")
@Data
@NoArgsConstructor
public class CurrencyExchangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String currencyFrom;

    @Column(nullable = false)
    private String currencyTo;

    @Column(nullable = false)
    private BigDecimal rate;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
