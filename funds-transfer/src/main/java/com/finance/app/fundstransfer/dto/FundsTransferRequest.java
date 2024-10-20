package com.finance.app.fundstransfer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Data transfer object for funds transfer requests.
 * Captures the details required to process a funds transfer
 * <p>
 * Validation annotations ensure the fields are validated before processing the transfer.
 * </p>
 *
 * @author <a href="https://github.com/Elaserph">elaserph</a>
 */
@Data
public class FundsTransferRequest implements Serializable {

    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    @NotBlank(message = "Sender account is required")
    private String senderAccount;

    @NotBlank(message = "Receiver account is required")
    private String receiverAccount;

    @NotNull(message = "Transfer amount is required")
    @Positive(message = "Transfer amount must be positive")
    private BigDecimal transferAmount;

    @NotBlank(message = "Transfer account currency is required")
    private String transferAccountCurrency;
}

