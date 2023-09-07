package com.techelevator.tenmo.datasource.JdbcDao;

import com.techelevator.tenmo.datasource.dao.TenmoTransferDao;
import com.techelevator.tenmo.datasource.model.TenmoAccount;
import com.techelevator.tenmo.datasource.model.TenmoTransfer;

// Import the enum types into the 'JdbcTenmoTransferDao' class
import com.techelevator.tenmo.datasource.model.TenmoTransfer.TRANSFER_STATUS;
import com.techelevator.tenmo.datasource.model.TenmoTransfer.TRANSFER_TYPE;


import com.techelevator.tenmo.exception.DaoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTenmoTransferDao implements TenmoTransferDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTenmoTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TenmoTransfer saveTransfer(TenmoTransfer aTransfer) {

        // TODONE: Given a Transfer object write a method to get add the Transfer to the data source
        JdbcTenmoAccountDao jdbcTenmoAccountDao = new JdbcTenmoAccountDao(jdbcTemplate); //declare JdbcTenmoAccountDao

        //Define an object to hold the return value
        TenmoTransfer newTransfer = null;

        //Test Exception
        if (aTransfer.getFromTenmoAccount().equals(aTransfer.getToTenmoAccount())) {
            throw new IllegalArgumentException("You cannot send money to yourself");
        }

        //1.Define a string to hold the SQL Statement
        String create = " INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                " VALUES(?,?,?,?,?) RETURNING transfer_id"; //RETURNING used to retrieve department_id that is generated by the database manager

        //2.Send the SQL statement database manager
        try{
            long newTransferId = jdbcTemplate.queryForObject(create, long.class, aTransfer.getTransferType().ordinal(), aTransfer.getTransferStatus().ordinal(), aTransfer.getFromTenmoAccount().getAccount_id(), aTransfer.getToTenmoAccount().getAccount_id(), aTransfer.getAmount());
            newTransfer = getATransferById(newTransferId);
            // If (account amount after transfer) >= 0, then we update amount on both account-from and account-to
            if (jdbcTenmoAccountDao.getAccountForAccountId(aTransfer.getFromTenmoAccount().getAccount_id()).getBalance().subtract(aTransfer.getAmount()).compareTo(BigDecimal.ZERO) > 0) {
                System.out.println("Transfer approved.");
                jdbcTenmoAccountDao.updateAccount(aTransfer.getFromTenmoAccount());
                jdbcTenmoAccountDao.updateAccount(aTransfer.getToTenmoAccount());
                // Update transfer status
                aTransfer.setTransferStatus(TenmoTransfer.TRANSFER_STATUS.APPROVED);
            } else {
                System.out.println ("Transfer rejected.");
                aTransfer.setTransferStatus(TenmoTransfer.TRANSFER_STATUS.REJECTED);
            }
        } catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return newTransfer;
    }

    @Override
    public List<TenmoTransfer> getTransfersForUser(int userId) {

        // TODO: Given a user id, write a method to retrieve all transfers for that user id from the data source
        List<TenmoTransfer> allTransfers = new ArrayList<>();
        // Define the SQL query with joins to retrieve detailed transfer information
        String sql = "SELECT t.*, " +
                "       tt.transfer_type_desc, " +
                "       ts.transfer_status_desc, " +
                "       t.account_from, " +
                "       t.account_to " +
                "FROM transfer t " +
                "JOIN transfer_type tt ON t.transfer_type_id = tt.transfer_type_id " +
                "JOIN transfer_status ts ON t.transfer_status_id = ts.transfer_status_id " +
                "JOIN account a ON t.account_from = a.account_id " +
                "JOIN tenmo_user tu ON a.user_id = tu.user_id " +
                "WHERE a.user_id = ? " +
                "ORDER BY a.user_id; ";

        try {
            // Execute the query and retrieve the transfer data
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

            // Iterate through the query results and map each row to a TenmoTransfer object
            while (results.next()) {
                TenmoTransfer transferResult = mapRowToTransfer(results);
                allTransfers.add(transferResult);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        // Return the list of retrieved TenmoTransfer objects
        return allTransfers;

    }

    @Override
    public TenmoTransfer getATransferById(Long transferIdRequested) {

        // TODONE: Given a transfer id, retrieve the Transfer from the data source
        // Define the SQL query to retrieve a transfer by its ID
        TenmoTransfer transfer = null;
        String sql = "SELECT t.transfer_id, t.created_at " +
                "       tt.transfer_type_desc, " +
                "       ts.transfer_status_desc, " +
                "       t.account_from, " +
                "       t.account_to " +
                "FROM transfer t " +
                "JOIN transfer_type tt ON t.transfer_type_id = tt.transfer_type_id " +
                "JOIN transfer_status ts ON t.transfer_status_id = ts.transfer_status_id " +
                "WHERE t.transfer_id = ?" +
                "ORDER BY t.amount; ";

        try {
            // Execute the query and retrieve the transfer data
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferIdRequested);

            // Check if the result contains data
            if (result.next()) {
                // Map the row data to a TenmoTransfer object
                transfer =  mapRowToTransfer(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return transfer;
    }

    private TenmoTransfer mapRowToTransfer (SqlRowSet results) {
        TenmoTransfer transfer = new TenmoTransfer();
        JdbcTenmoAccountDao account = new JdbcTenmoAccountDao(jdbcTemplate);
        transfer.setAmount(results.getBigDecimal("amount"));
        if (results.getTimestamp("created_at") != null) {
            transfer.setCreate(results.getTimestamp("created_at"));
        }
        transfer.setTransferId(results.getLong("transfer_id"));
        transfer.setFromTenmoAccount(account.getAccountForAccountId((long)(results.getInt("account_from"))));
        transfer.setToTenmoAccount(account.getAccountForAccountId((long)(results.getInt("account_to"))));
        transfer.setAmount(results.getBigDecimal("amount"));

        // The Model (TenmoTransfer.java) initializes this fields as enums, the database stores them as numeric values
        // Since the numeric values stored in the database match the index positions of the enum constants,
        //      the switch cases will map the numeric values correctly to the corresponding enum constants

        // Mapping for TransferStatus
        int transferStatusValue = results.getInt("transfer_status_id");
        transfer.setTransferStatus(mapIntToTransferStatus(transferStatusValue));

        // Mapping for TransferType
        int transferTypeValue = results.getInt("transfer_type_id");
        transfer.setTransferType(mapIntToTransferType(transferTypeValue));

        return transfer;
    }

    // These methods take the integer value the database stores and maps it to the appropriate enum constant
    private TRANSFER_STATUS mapIntToTransferStatus(int value) {
        switch (value) {
            case 1: return TRANSFER_STATUS.PENDING;
            case 2: return TRANSFER_STATUS.APPROVED;
            case 3: return TRANSFER_STATUS.REJECTED;
            default: return TRANSFER_STATUS.NONE;
        }
    }

    private TRANSFER_TYPE mapIntToTransferType(int value) {
        switch (value) {
            case 1: return TRANSFER_TYPE.REQUEST;
            case 2: return TRANSFER_TYPE.SEND;
            default: return TRANSFER_TYPE.NONE;
        }
    }

}