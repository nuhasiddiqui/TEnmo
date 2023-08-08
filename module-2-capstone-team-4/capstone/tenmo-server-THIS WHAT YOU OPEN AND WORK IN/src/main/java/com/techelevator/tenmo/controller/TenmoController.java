package com.techelevator.tenmo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TenmoController {

    /**
     * Handles an HTTP POST request for the path: /account
     *
     * Add a TenmoAccount object to  the datasource
     *
     * @param aNewAccount - must be present in the request body as a valid JSON object for a TenmoAccount object
     *                      Note: If an account is sent in the JSON object it will be ignored as the data source
     *                            manager will assign a unique account id when storing the TenmoObject
     *
     * @return the TenmoAccount object with the data source assigned accountId
     */

    // TODO: Write a controller to handle an HTTP POST request for the path: /account


    /**
     * Handle an HTTP GET request for the path: /account/{accountId}
     *
     * Return the account from the data source with the accountId provided
     *
     * Note: The accountId requested must be specified as a path variable in the request
     *
     * @param theAcctId - must be specified as a path variable
     *
     * @return the TenmoAccount object for the accountId specified or null
     */

    // TODO: Write a controller to handle an HTTP GET request for the path: /account/{accountId}

    /**
     * Handle an HTTP GET request for either the path: /account
     *                                             or: /account?id=userId
     *
     * if the /account path is used for the request, all TenmoAccounts in the datasource will be returned
     *
     * if the /account?id=usedId path is used in the request, all accounts for the specified userid will be returned
     *
     * @param theUserId - optional query parameter to request all accounts for a specific userid
     *
     * @return - a list containing all accounts indicated by the path or an empty list if no accounts found
     */

    // TODO: Write a controller to handle a GET request for either the path: /account
    //                                                                   or: /account?id=userId

    /**
     * Handles an HTTP GET request for path /user
     *
     * @return - a list of all users in the data source
     */

    // TODO: Write a controller to handle a  GET request for path /user

    /**
     * Handles an HTTP PUT request for the path: /account
     *
     * Add a TenmoAccount object to  the datasource
     *
     * @param theUpdatedAcct - must be present in the request body as a valid JSON object for a TenmoAccount object
     *
     *
     * @return the update TenmoAccount object from the datasource
     */

    // TODO: Write a controller to handle an HTTP PUT request for the path: /account

    /**
     * Handles an HTTP POST request for the path: /transfer
     *
     * Add a TenmoTransfer object to  the datasource
     *
     * @param theTransfer - must be present in the request body as a valid JSON object for a TenmoTransfer object
     *
     *
     * @return the update TenmoTransfer object from the datasource
     */

    // TODO: Write a controller to handle an HTTP POST request for the path: /transfer

    /**
     * Handles HTTP GET for path /transfer?id=userid
     *
     * Return all transfer for the userid given
     *
     * @param id - the userid whose transfers should be returned
     */

    // TODO: Write a controller to handles HTTP GET for path /transfer?id=userid

    /**
     * Helper method to log API calls made to the server
     *
     * @param message - message to be included in the server log
     */
    public void logAPICall(String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.A");
        String timeNow = now.format(formatter);
        System.out.println(timeNow + "-" + message);
    }

}
