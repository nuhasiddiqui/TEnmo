package com.techelevator.tenmo.datasource.JdbcDao;

import com.techelevator.tenmo.datasource.dao.TenmoAccountDao;
import com.techelevator.tenmo.datasource.model.TenmoAccount;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//'JdbcTenmoAccountDao' is responsible for providing data access operations related to the account table
@Component
public class JdbcTenmoAccountDao implements TenmoAccountDao {

    // Declare a private instance variable named 'jdbcTemplate'
    //      type: JdbcTemplate, a class provided by the Spring Framework that simplifies JDBC databse access
    //      'JdbcTemplate' is used to interact with the database using JDBC
    private final JdbcTemplate jdbcTemplate;

    public JdbcTenmoAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<TenmoAccount> getAllAccounts() {

        // TODONE: Write a method to get all accounts from the data source

        // Create an ArrayList to hold the retrieved TenmoAccount objects
        List <TenmoAccount> tenmoAccounts = new ArrayList<>();
        // Define the SQL query to select all rows from the 'account' table
        String sql = "SELECT * FROM account;";

        // Execute the query and retrieve the results as a SqlRowSet
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        // Iterate over the results and map each row to a TenmoAccount object
        while (results.next()) {
            tenmoAccounts.add(mapRowToTenmoAccount(results));
        }
        // Return the list of TenmoAccount objects
        return tenmoAccounts;
    }


    @Override
    public List<TenmoAccount> getAccountsForAUserId(int theUserId) {

        // TODONE: Write a method to get all accounts for a particular user id from the data source
        List<TenmoAccount> userAccounts = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE user_id = ?";

        // Execute the query and retrieve the account data
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, theUserId);
        while (results.next()) {
            TenmoAccount account = mapRowToTenmoAccount(results);
            userAccounts.add(account);
        }
        return userAccounts;
    }

    @Override
    public TenmoAccount getAccountForAccountId(Long theAccountId) {

        // TODONE: Given an account id, write a method to get a specific account from the data source
        // Construct the SQL query to select the account with the provided account ID
        String sql = "SELECT * FROM account WHERE account_id = ?";

        // Execute the query and retrieve the account data
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, theAccountId);
        // Check if the result contains date
        if (result.next()) {
            // Map the row data to a TenmoAccount object
            return mapRowToTenmoAccount(result);
        } else {
            return null;
        }
    }


    @Override
    public TenmoAccount saveAccount(TenmoAccount tenmoAccount2Save) {

        // TODONE: Given an Account object write a method to get add an account to the data source
        TenmoAccount newAccount = null;
        String sql = "INSERT INTO account (account_id, user_id, balance) " +
                     "VALUES (?, ?, ?) RETURNING account_id;";
        int newAccountId = jdbcTemplate.queryForObject(sql, int.class,
                tenmoAccount2Save.getAccount_id(), tenmoAccount2Save.getUser_id(), tenmoAccount2Save.getBalance());
        newAccount = getAccountForAccountId((long)newAccountId);
        return newAccount;
    }

    @Override
    public TenmoAccount updateAccount(TenmoAccount tenmoAccount2Update) {

        // TODONE: Given an Account object write a method to get update the account in the data source
        TenmoAccount updatedAccount = null;
        String sql = "UPDATE account SET account_id = ?, user_id = ?, balance = ? " +
                     "WHERE account_id = ?";
        int numberOfRows = jdbcTemplate.update(sql, tenmoAccount2Update.getAccount_id(),
                tenmoAccount2Update.getUser_id(), tenmoAccount2Update.getBalance());
        if (numberOfRows > 0) {
            updatedAccount = getAccountForAccountId(tenmoAccount2Update.getAccount_id());
        }
        return updatedAccount;
    }

    @Override
    public BigDecimal getBalanceByAccountId(int AccountId) {
        // TODOne: User can get balance from an account
        TenmoAccount account = null;
        BigDecimal balance = new BigDecimal("0");
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, AccountId);
        if (results.next()) {
            account = mapRowToTenmoAccount(results);
            balance = account.getBalance();
        }
        return balance;
    }

    private TenmoAccount mapRowToTenmoAccount(SqlRowSet results) {
        TenmoAccount tenmoAccount = new TenmoAccount();
        tenmoAccount.setAccount_id((long)results.getInt("account_id"));
        tenmoAccount.setUser_id(results.getInt("user_id"));
        tenmoAccount.setBalance(results.getBigDecimal("balance"));
        // set created_at
        return tenmoAccount;

    }

}