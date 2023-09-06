package com.techelevator.tenmo.datasource.dao;

import com.techelevator.tenmo.datasource.model.TenmoAccount;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

public interface TenmoAccountDao {

    List<TenmoAccount> getAllAccounts();

    TenmoAccount getAccountForAccountId(Long theAccountId);

    List<TenmoAccount> getAccountsForAUserId(int anUserId);

    TenmoAccount saveAccount(TenmoAccount tenmoAccount2Save);

    TenmoAccount updateAccount(TenmoAccount tenmoAccount2Update);

    BigDecimal getBalanceByAccountId(int AccountId);

}