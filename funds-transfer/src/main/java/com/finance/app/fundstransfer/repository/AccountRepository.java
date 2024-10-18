package com.finance.app.fundstransfer.repository;

import com.finance.app.fundstransfer.entity.AccountEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AccountEntity a WHERE a.accountNumber = :accountNumber AND a.owner.id = :ownerId")
    AccountEntity findByAccountNumberAndOwnerIdForUpdate(@Param("accountNumber") String accountNumber, @Param("ownerId") Long ownerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AccountEntity a WHERE a.accountNumber = :accountNumber")
    AccountEntity findByAccountNumberForUpdate(@Param("accountNumber") String accountNumber);
}
