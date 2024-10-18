package com.finance.app.fundstransfer.service;

import com.finance.app.fundstransfer.dto.FundsTransferRequest;
import com.finance.app.fundstransfer.entity.AccountEntity;
import com.finance.app.fundstransfer.exception.InsufficientFundsException;
import com.finance.app.fundstransfer.exception.ResourceNotFoundException;
import com.finance.app.fundstransfer.repository.AccountRepository;
import com.finance.app.fundstransfer.wrapper.CurrencyExchangeApiWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrencyExchangeApiWrapper currencyExchangeApiWrapper;

    @Transactional
    public Boolean transferFunds(FundsTransferRequest request) {
        AccountEntity senderAccount = accountRepository.findByAccountNumberAndOwnerIdForUpdate(request.getSenderAccount(), request.getOwnerId());
        AccountEntity receiverAccount = accountRepository.findByAccountNumberForUpdate(request.getReceiverAccount());

        //validate the details
        validateTransfer(request, senderAccount, receiverAccount);
        //get total amount to be transferred
        BigDecimal transferAmountInReceiverCurrency = getTransferableAmount(request, senderAccount, receiverAccount);

        // Perform transfer and update balance
        senderAccount.setBalance(senderAccount.getBalance().subtract(request.getTransferAmount()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(transferAmountInReceiverCurrency));
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        return true;  // Transfer successful
    }

    private void validateTransfer(FundsTransferRequest request, AccountEntity senderAccount, AccountEntity receiverAccount) {
        if (senderAccount == null) {
            throw new ResourceNotFoundException("Sender account or Owner not found!");  // Invalid request
        }
        if (receiverAccount == null) {
            throw new ResourceNotFoundException("Receiver account is not found!");  // Invalid request
        }
        if (senderAccount.getBalance().compareTo(request.getTransferAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds, check balance!");  // Insufficient funds
        }
    }

    private BigDecimal getTransferableAmount(FundsTransferRequest request, AccountEntity senderAccount, AccountEntity receiverAccount) {
        // Fetch the current exchange rate via external api call
        BigDecimal exchangeRate = currencyExchangeApiWrapper.getExchangeRate(senderAccount.getCurrency(), receiverAccount.getCurrency());
        return request.getTransferAmount().multiply(exchangeRate);
    }

}
