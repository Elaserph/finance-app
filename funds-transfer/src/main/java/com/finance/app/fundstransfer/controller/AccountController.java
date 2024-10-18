package com.finance.app.fundstransfer.controller;

import com.finance.app.fundstransfer.dto.FundsTransferRequest;
import com.finance.app.fundstransfer.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(path = {
            "/funds-transfer",
            "/funds-transfer/v1"
    })
    public ResponseEntity<String> transferFunds(@Valid @RequestBody FundsTransferRequest request) {
        var success = accountService.transferFunds(request);
        return success ? ResponseEntity.ok("Success!") : ResponseEntity.badRequest().body("Transfer failed!");
    }
}
