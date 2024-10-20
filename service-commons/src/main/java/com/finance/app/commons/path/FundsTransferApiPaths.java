package com.finance.app.commons.path;

/**
 * Utility class for managing API paths related to funds transfer.
 * Provides constants for common paths and utility methods for generating specific paths.
 * This class is final and cannot be instantiated.
 *
 * @author
 * <a href="https://github.com/Elaserph">elaserph</a>
 */
public final class FundsTransferApiPaths {

    public static final String ROOT_PATH = "/api";
    public static final String FUNDS_TRANSFER_PATH = "/funds-transfer";
    public static final String FUNDS_TRANSFER_PATH_V1 = "/funds-transfer/v1";

    /**
     * Private constructor to prevent instantiation.
     */
    private FundsTransferApiPaths() {

    }

    /**
     * Generates the API path for initiating a funds transfer.
     *
     * @param version the API version, e.g., "1", "2", pass null for the default version.
     * @return the full API path for initiating the funds transfer.
     */
    public static String getFundsTransferApiPath(String version) {
        return ROOT_PATH + FUNDS_TRANSFER_PATH + (version != null ? "/v" + version : "");
    }
}
