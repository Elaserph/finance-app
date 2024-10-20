package com.finance.app.fundstransfer.service;

import com.finance.app.fundstransfer.dto.FundsTransferRequest;
import com.finance.app.fundstransfer.entity.AccountEntity;
import com.finance.app.fundstransfer.exception.InsufficientFundsException;
import com.finance.app.fundstransfer.exception.ResourceNotFoundException;
import com.finance.app.fundstransfer.repository.AccountRepository;
import com.finance.app.fundstransfer.wrapper.CurrencyExchangeApiWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.PessimisticLockingFailureException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceUnitTest {

    @Mock
    private AccountRepository accountRepositoryTest;

    @Mock
    private CurrencyExchangeApiWrapper currencyExchangeApiWrapperTest;

    @InjectMocks
    private AccountService accountServiceTest;

    private FundsTransferRequest requestTest;
    private AccountEntity senderAccountTest;
    private AccountEntity receiverAccountTest;

    @BeforeEach
    public void setUp() {
        //this is the FundsTransferRequest to be passed to the service method transferFunds(FundsTransferRequest)
        requestTest = new FundsTransferRequest();
        requestTest.setOwnerId(1L);
        requestTest.setSenderAccount("ACC123");
        requestTest.setReceiverAccount("ACC456");
        requestTest.setTransferAmount(BigDecimal.valueOf(300));
        requestTest.setTransferAccountCurrency("USD");

        //sender account details to be retrieved from db
        senderAccountTest = new AccountEntity();
        senderAccountTest.setBalance(BigDecimal.valueOf(500));
        senderAccountTest.setCurrency("USD");

        //receiver account details to be retrieved from db
        receiverAccountTest = new AccountEntity();
        receiverAccountTest.setBalance(BigDecimal.valueOf(500));
        receiverAccountTest.setCurrency("USD");
    }

    @Test
    void testTransferFunds_Success() {
        when(accountRepositoryTest.findByAccountNumberAndOwnerIdForUpdate(anyString(), anyLong())).thenReturn(senderAccountTest);
        when(accountRepositoryTest.findByAccountNumberForUpdate(anyString())).thenReturn(receiverAccountTest);

        assertTrue(accountServiceTest.transferFunds(requestTest));
        verify(accountRepositoryTest, times(1)).save(senderAccountTest);
        verify(accountRepositoryTest, times(1)).save(receiverAccountTest);
    }

    @Test
    void testTransferFunds_InsufficientFunds() {
        senderAccountTest.setBalance(BigDecimal.valueOf(100));

        when(accountRepositoryTest.findByAccountNumberAndOwnerIdForUpdate(anyString(), anyLong())).thenReturn(senderAccountTest);
        when(accountRepositoryTest.findByAccountNumberForUpdate(anyString())).thenReturn(receiverAccountTest);

        assertThrows(InsufficientFundsException.class, () -> accountServiceTest.transferFunds(requestTest));
    }

    @Test
    void testTransferFunds_ReceiverAccountNotFound() {
        when(accountRepositoryTest.findByAccountNumberAndOwnerIdForUpdate(anyString(), anyLong())).thenReturn(senderAccountTest);
        when(accountRepositoryTest.findByAccountNumberForUpdate(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> accountServiceTest.transferFunds(requestTest));
    }

    @Test
    void testTransferFunds_SenderAccountNotFound() {
        when(accountRepositoryTest.findByAccountNumberAndOwnerIdForUpdate(anyString(), anyLong())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> accountServiceTest.transferFunds(requestTest));
    }

    @Test
    void testTransferFunds_ConcurrentAccessException() {
        when(accountRepositoryTest.findByAccountNumberAndOwnerIdForUpdate(anyString(), anyLong()))
                .thenThrow(new PessimisticLockingFailureException("Concurrent access"));

        assertThrows(PessimisticLockingFailureException.class, () -> accountServiceTest.transferFunds(requestTest));
    }

    @Test
    void testTransferFunds_SenderReceiverAccountsSame() {
        requestTest.setReceiverAccount("ACC123"); //set receiver account same as sender's

        assertThrows(IllegalArgumentException.class, () -> accountServiceTest.transferFunds(requestTest));
    }

    @Test
    void testTransferFunds_SenderAccountCurrencyMismatch() {
        //set requested amount's currency to INR -> mismatch with sender account's currency, USD
        //usually transferAccountCurrency will be filled by system/client-ui, based on the logged-in/selected sender account's details
        requestTest.setTransferAccountCurrency("INR");

        when(accountRepositoryTest.findByAccountNumberAndOwnerIdForUpdate(anyString(), anyLong())).thenReturn(senderAccountTest);
        when(accountRepositoryTest.findByAccountNumberForUpdate(anyString())).thenReturn(receiverAccountTest);

        assertThrows(IllegalArgumentException.class, () -> accountServiceTest.transferFunds(requestTest));
    }
}