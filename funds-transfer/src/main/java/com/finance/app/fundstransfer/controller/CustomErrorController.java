package com.finance.app.fundstransfer.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Global custom controller for rendering errors in the application.
 * Implements Spring's {@link ErrorController} to provide custom error handling.
 *
 * @author <a href="https://github.com/Elaserph">elaserph</a>
 */
@RestController
public class CustomErrorController implements ErrorController {

    /**
     * Handles errors and returns a custom error message.
     *
     * @param request {@link HttpServletRequest} that resulted routing to /error
     * @return a custom error message, including the HTTP status code if available.
     */
    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return status != null ? "Oops! where are you going? status code: " + Integer.valueOf(status.toString()) : "Unknown error, Oops!";
    }
}
