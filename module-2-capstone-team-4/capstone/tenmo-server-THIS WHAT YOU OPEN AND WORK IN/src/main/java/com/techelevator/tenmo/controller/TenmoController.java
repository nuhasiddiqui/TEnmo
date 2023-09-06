package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.datasource.JdbcDao.JdbcTenmoAccountDao;
import com.techelevator.tenmo.datasource.JdbcDao.JdbcTenmoTransferDao;
import com.techelevator.tenmo.datasource.JdbcDao.JdbcTenmoUserDao;
import com.techelevator.tenmo.datasource.model.TenmoAccount;
import com.techelevator.tenmo.datasource.model.TenmoTransfer;
import com.techelevator.tenmo.datasource.model.TenmoUser;
import com.techelevator.tenmo.exception.DaoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TenmoController {

    private final JdbcTenmoAccountDao tenmoAccountDao;
    private final JdbcTenmoTransferDao tenmoTransferDao;
    private final JdbcTenmoUserDao tenmoUserDao;

    // Constructor
    public TenmoController(JdbcTenmoAccountDao accountDao, JdbcTenmoTransferDao transferDao, JdbcTenmoUserDao userDao){
        this.tenmoAccountDao = accountDao;
        this.tenmoTransferDao = transferDao;
        this.tenmoUserDao = userDao;
    }

    // Creates a new TenmoAccount
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/account", method = RequestMethod.POST)
    public TenmoAccount createAccount(@Valid @RequestBody TenmoAccount aNewAccount) {
        return tenmoAccountDao.saveAccount(aNewAccount);
    }

    // Retrieves a TenmoAccount by its ID
    @RequestMapping(path = "/account/{accountId}", method = RequestMethod.GET)
    public TenmoAccount getAccountById(@PathVariable long theAcctId) {
        return tenmoAccountDao.getAccountForAccountId(theAcctId);
    }

    // Retrieves either all TenmoAccounts or accounts for a specific user by user ID
    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public List<TenmoAccount> getAccountById(@RequestParam(name = "userid", defaultValue = "0") int theUserId) {
        if (theUserId == 0) {
            return tenmoAccountDao.getAllAccounts();
        } else {
            return tenmoAccountDao.getAccountsForAUserId(theUserId);
        }
    }

    // Retrieves a list of all TenmoUsers
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public List<TenmoUser> getAllUsers(@RequestParam(defaultValue = "0") int userid) {
        List<TenmoUser> users = new ArrayList<>();
        if (userid == 0){
            return tenmoUserDao.getAllUsernames();
        } else {
            users.add(tenmoUserDao.getUsernameById(userid));
            return users;
        }
    }

    // Updates an existing TenmoAccount
    @RequestMapping(path = "/account", method = RequestMethod.PUT)
    public TenmoAccount updateAccount(@Valid @RequestBody TenmoAccount theUpdatedAcct) {
        try {
            TenmoAccount updatedTenmoAccount = tenmoAccountDao.updateAccount(theUpdatedAcct);
            return updatedTenmoAccount;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
        }
    }

    // Creates a new TenmoTransfer
    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public TenmoTransfer createTransfer(@Valid @RequestBody TenmoTransfer theTransfer) {
        return tenmoTransferDao.saveTransfer(theTransfer);
    }

    // Retrieves a list of TenmoTransfers for a specific user by user ID
    @RequestMapping(path = "/transfer", method = RequestMethod.GET)
    public List<TenmoTransfer> getTransferByUserId(@RequestParam int id) {
        return tenmoTransferDao.getTransfersForUser(id);
    }

    // Helper method to log API calls made to the server
    public void logAPICall(String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.A");
        String timeNow = now.format(formatter);
        System.out.println(timeNow + "-" + message);
    }
}