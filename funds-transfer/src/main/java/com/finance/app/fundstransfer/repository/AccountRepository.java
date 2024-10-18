package com.finance.app.fundstransfer.repository;

import com.finance.app.fundstransfer.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

}
