package com.finance.app.fundstransfer.exception;

/**
 * Custom Exception thrown when resource like sender or receiver account
 * or exchange rate is not found
 * <p>
 * Extends {@link RuntimeException}.
 * </p>
 *
 * @author <a href="https://github.com/Elaserph">elaserph</a>
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
