package com.finance.app.fundstransfer.repository;

import com.finance.app.fundstransfer.entity.AccountEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for managing AccountEntity operations.
 *
 * @author <a href="https://github.com/Elaserph">elaserph</a>
 */
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    /**
     * Finds an account by account number and owner ID with a pessimistic write lock.
     *
     * @param accountNumber the account number.
     * @param ownerId       the owner's ID.
     * @return the matching account entity.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AccountEntity a WHERE a.accountNumber = :accountNumber AND a.owner.id = :ownerId")
    AccountEntity findByAccountNumberAndOwnerIdForUpdate(@Param("accountNumber") String accountNumber, @Param("ownerId") Long ownerId);

    /**
     * Finds an account by account number with a pessimistic write lock.
     *
     * @param accountNumber the account number.
     * @return the matching account entity.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AccountEntity a WHERE a.accountNumber = :accountNumber")
    AccountEntity findByAccountNumberForUpdate(@Param("accountNumber") String accountNumber);
}
