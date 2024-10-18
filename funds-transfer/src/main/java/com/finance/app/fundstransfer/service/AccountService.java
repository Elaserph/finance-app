package com.finance.app.fundstransfer.service;

import com.finance.app.fundstransfer.dto.FundsTransferRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Transactional
    public Boolean transferFunds(FundsTransferRequest request) {

        return false;
    }
}
