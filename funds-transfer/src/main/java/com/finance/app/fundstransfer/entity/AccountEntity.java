package com.finance.app.fundstransfer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entity representing an account.
 * Maps to the "account" table in the database.
 * Provides details about an account, including its owner, account number, type, currency, and balance.
 *
 * @author <a href="https://github.com/Elaserph">elaserph</a>
 */
@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private CustomerEntity owner;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String accountType; //debit or credit

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private BigDecimal balance;
}
