package com.finance.app.commons.path;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FundsTransferApiPathsTest {

    @Test
    void getFundsTransferApiPath_Default() {
        //arrange,act,assert
        String expected = "/api/funds-transfer";
        String actual = FundsTransferApiPaths.getFundsTransferApiPath(null);
        assertEquals(expected, actual);
    }

    @Test
    void getFundsTransferApiPath_V1() {
        //arrange,act,assert
        String expected = "/api/funds-transfer/v1";
        String actual = FundsTransferApiPaths.getFundsTransferApiPath("1");
        assertEquals(expected, actual);
    }
}