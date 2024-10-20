package com.finance.app.fundstransfer.exception;

/**
 * Custom Exception thrown when there are insufficient funds to complete a transaction.
 * <p>
 * Extends {@link RuntimeException}.
 * </p>
 *
 * @author <a href="https://github.com/Elaserph">elaserph</a>
 */
public class InsufficientFundsException extends RuntimeException {

    /**
     * Constructs a new InsufficientFundsException with the specified detail message.
     *
     * @param message the detail message.
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
