package com.finance.app.fundstransfer.service;

import com.finance.app.fundstransfer.dto.FundsTransferRequest;
import com.finance.app.fundstransfer.entity.AccountEntity;
import com.finance.app.fundstransfer.exception.InsufficientFundsException;
import com.finance.app.fundstransfer.exception.ResourceNotFoundException;
import com.finance.app.fundstransfer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Boolean transferFunds(FundsTransferRequest request) {
        AccountEntity senderAccount = accountRepository.findByAccountNumberAndOwnerIdForUpdate(request.getSenderAccount(), request.getOwnerId());
        AccountEntity receiverAccount = accountRepository.findByAccountNumberForUpdate(request.getReceiverAccount());

        if (senderAccount == null || receiverAccount == null) {
            throw new ResourceNotFoundException("Sender or Receiver account is not found!");  // Invalid request
        }
        if (senderAccount.getBalance().compareTo(request.getTransferAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient Funds!");  // Insufficient funds
        }

        // Perform transfer and update balance
        senderAccount.setBalance(senderAccount.getBalance().subtract(request.getTransferAmount()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(request.getTransferAmount()));
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        return true;  // Transfer successful
    }
}
