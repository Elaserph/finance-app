package com.finance.app.fundstransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FundsTransferApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundsTransferApplication.class, args);
        System.out.println("Funds Transfer app!!!");
    }

}
