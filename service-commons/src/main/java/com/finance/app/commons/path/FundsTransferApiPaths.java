package com.finance.app.commons.path;

public final class FundsTransferApiPaths {

    private FundsTransferApiPaths(){

    }

    public static final String ROOT_PATH = "/api";
    public static final String FUNDS_TRANSFER_PATH = "/funds-transfer";
    public static final String FUNDS_TRANSFER_PATH_V1 = "/funds-transfer/v1";

    public static String getFundsTransferApiPath(String version){
        return ROOT_PATH +  FUNDS_TRANSFER_PATH +(version != null ? "/v" + version : "");
    }
}
