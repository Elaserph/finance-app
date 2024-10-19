package com.finance.app.fundstransfer.controller;

import com.finance.app.commons.path.FundsTransferApiPaths;
import com.finance.app.fundstransfer.dto.FundsTransferRequest;
import com.finance.app.fundstransfer.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(FundsTransferApiPaths.ROOT_PATH)
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(path = {
            FundsTransferApiPaths.FUNDS_TRANSFER_PATH,
            FundsTransferApiPaths.FUNDS_TRANSFER_PATH_V1
    })
    public ResponseEntity<String> transferFunds(@Valid @RequestBody FundsTransferRequest request) {
        accountService.transferFunds(request);
        return ResponseEntity.ok("Success!");
    }
}
