package com.techelevator.tenmo.datasource.JdbcDao;

import com.techelevator.tenmo.datasource.dao.TenmoAccountDao;
import com.techelevator.tenmo.datasource.model.TenmoAccount;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcTenmoAccountDao implements TenmoAccountDao {

    @Override
    public List<TenmoAccount> getAllAccounts() {

        // TODO: Write a method to get all accounts from the data source

        return null;
    }


    @Override
    public List<TenmoAccount> getAccountsForAUserId(int theUserId) {

        // TODO: Write a method to get all accounts for a particular user id from the data source

        return null;
    }

    @Override
    public TenmoAccount getAccountForAccountId(Long theAccountId) {

        // TODO: Given an account id, write a method to get a specific account from the data source

        return null;
    }


    @Override
    public TenmoAccount saveAccount(TenmoAccount tenmoAccount2Save) {

        // TODO: Given an Account object write a method to get add an account to the data source

        return null;
    }

    @Override
    public TenmoAccount updateAccount(TenmoAccount tenmoAccount2Update) {

        // TODO: Given an Account object write a method to get update the account in the data source

        return null;
    }

}
