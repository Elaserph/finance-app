package com.finance.app.fundstransfer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entity representing a customer.
 * Maps to the "customer" table in the database.
 * Provides details about a customer, including their name, email, and associated accounts.
 * <p>
 * Each customer can have multiple associated accounts (debit, credit), represented by the {@link AccountEntity} class.
 * </p>
 *
 * @author <a href="https://github.com/Elaserph">elaserph</a>
 */
@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AccountEntity> accounts;
}
