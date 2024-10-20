package com.finance.app.fundstransfer.service;

import com.finance.app.fundstransfer.dto.FundsTransferRequest;
import com.finance.app.fundstransfer.entity.AccountEntity;
import com.finance.app.fundstransfer.exception.InsufficientFundsException;
import com.finance.app.fundstransfer.exception.ResourceNotFoundException;
import com.finance.app.fundstransfer.repository.AccountRepository;
import com.finance.app.fundstransfer.wrapper.CurrencyExchangeApiWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

/**
 * Service for handling account operations, including transferring funds between accounts.
 * This class provides methods for validating transfer requests, calculating transferable amounts using exchange rate,
 * and performing the actual fund transfer while ensuring data consistency and transactional integrity.
 *
 * @author <a href="https://github.com/Elaserph">elaserph</a>
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrencyExchangeApiWrapper currencyExchangeApiWrapper;

    /**
     * Transfers funds between accounts.
     * <p>
     * This method validates the transfer request, calculates the transferable amount using the exchange rate api wrapper,
     * performs the transfer, and updates account balances. It ensures transactional integrity and consistency.
     * </p>
     *
     * @param request the transfer request containing details such as sender account, receiver account, and amount.
     * @return true if the transfer is successful.
     * @throws ResourceNotFoundException          if the sender or receiver account or exchange rate is not found.
     * @throws InsufficientFundsException         if the sender account has insufficient funds.
     * @throws HttpClientErrorException           when an HTTP 4xx is received from exchange rate api
     * @throws PessimisticLockingFailureException Exception thrown on a pessimistic locking violation.
     * @throws IllegalArgumentException           in case the given entity is null, if transfer accounts are same
     *                                            or sender account currency mismatches with transfer currency
     */
    @Transactional
    public Boolean transferFunds(FundsTransferRequest request) {
        if (request.getSenderAccount().equals(request.getReceiverAccount())) {
            //throw exception if accounts are same
            throw new IllegalArgumentException("Transfer failed! Sender and receiver accounts must not be the same");
        }
        //get account details
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

    /**
     * Validates the transfer request.
     * <p>
     * Checks whether the sender and receiver accounts exist and have sufficient funds.
     * </p>
     *
     * @param request         the transfer request.
     * @param senderAccount   the sender's account entity.
     * @param receiverAccount the receiver's account entity.
     * @throws ResourceNotFoundException  if the sender or receiver account is not found.
     * @throws InsufficientFundsException if the sender account has insufficient funds.
     */
    private void validateTransfer(FundsTransferRequest request, AccountEntity senderAccount, AccountEntity receiverAccount) {
        if (senderAccount == null) {
            throw new ResourceNotFoundException("Transfer failed! Sender account or Owner not found!");  //Invalid request
        }
        if (receiverAccount == null) {
            throw new ResourceNotFoundException("Transfer failed! Receiver account is not found!");  //Invalid request
        }
        if (senderAccount.getBalance().compareTo(request.getTransferAmount()) < 0) {
            throw new InsufficientFundsException("Transfer failed! Insufficient funds, check balance!");  //Insufficient funds
        }
        if (!request.getTransferAccountCurrency().equalsIgnoreCase(senderAccount.getCurrency())) {
            throw new IllegalArgumentException("Transfer failed! transfer amount currency different from sender account currency."); // Invalid request
        }
    }

    /**
     * Calculates the transferable amount in the receiver's currency.
     * <p>
     * Fetches the current exchange rate via an external API call and converts the transfer amount.
     * </p>
     *
     * @param request         the funds transfer request containing transfer details.
     * @param senderAccount   the sender's account entity.
     * @param receiverAccount the receiver's account entity.
     * @return the transferable amount in the receiver's currency.
     * @throws ResourceNotFoundException if exchange rate is not found.
     * @throws HttpClientErrorException  if when an HTTP 4xx is received from exchange rate api
     */
    private BigDecimal getTransferableAmount(FundsTransferRequest request, AccountEntity senderAccount, AccountEntity receiverAccount) {
        BigDecimal exchangeRate = BigDecimal.ONE; //default exchange rate be 1
        if (!senderAccount.getCurrency().equalsIgnoreCase(receiverAccount.getCurrency())) { //compare currencies
            // Fetch the current exchange rate via external api call
            exchangeRate = currencyExchangeApiWrapper.getExchangeRate(senderAccount.getCurrency(), receiverAccount.getCurrency());
        }
        return request.getTransferAmount().multiply(exchangeRate);
    }

}
