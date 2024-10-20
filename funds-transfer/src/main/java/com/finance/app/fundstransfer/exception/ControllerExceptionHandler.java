package com.finance.app.fundstransfer.exception;

import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling various exceptions across the application.
 * <p>
 * This class is annotated with {@link ControllerAdvice} to indicate that it provides centralized exception handling across the application.
 * </p>
 * <p>
 * Handles specific exceptions such as validation errors, concurrency issues, insufficient funds, and resource not found,
 * providing meaningful error messages to the client. For other runtime exceptions, it provides a generic error response.
 * </p>
 *
 * @author <a href="https://github.com/Elaserph">elaserph</a>
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final String ERROR = "error";
    private static final String MESSAGE = "message";

    /**
     * Handles {@link MethodArgumentNotValidException} and returns a validation error response.
     *
     * @param ex the exception.
     * @return a {@link ResponseEntity} containing the validation error messages and a BAD_REQUEST status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR, "Arguments validation error");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                response.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link PessimisticLockingFailureException} and returns a concurrency error response.
     *
     * @param ex the exception.
     * @return a {@link ResponseEntity} containing the error message and a CONFLICT status.
     */
    @ExceptionHandler(PessimisticLockingFailureException.class)
    public ResponseEntity<Map<String, String>> handlePessimisticLockingFailureException(PessimisticLockingFailureException ex) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR, "Concurrent update error");
        response.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handles {@link InsufficientFundsException} and returns an insufficient funds error response.
     *
     * @param ex the exception.
     * @return a {@link ResponseEntity} containing the error message and a BAD_REQUEST status.
     */
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientFundsException(InsufficientFundsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR, "Insufficient Funds");
        response.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ResourceNotFoundException} and returns a resource not found error response.
     *
     * @param ex the exception.
     * @return a {@link ResponseEntity} containing the error message and a BAD_REQUEST status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR, "Resource not found");
        response.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link IllegalArgumentException} and returns an illegal argument error response.
     *
     * @param ex the exception.
     * @return a {@link ResponseEntity} containing the error message and a BAD_REQUEST status.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR, "Illegal arguments");
        response.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any {@link RuntimeException} that is not specifically handled by other methods.
     *
     * <p>one exception to rule them all!</p>
     *
     * @param ex the exception.
     * @return a {@link ResponseEntity} containing a generic error message and a BAD_REQUEST status.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR, "Unknown error");
        response.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
