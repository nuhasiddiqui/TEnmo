package com.techelevator.tenmo.datasource.JdbcDao;

import com.techelevator.tenmo.datasource.dao.TenmoTransferStatusDao;
import com.techelevator.tenmo.datasource.model.TenmoTransferStatus;
import com.techelevator.tenmo.datasource.model.TenmoUser;
import com.techelevator.tenmo.exception.DaoException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class JdbcTenmoTransferStatusDao implements TenmoTransferStatusDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTenmoTransferStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Retrieves the transfer status description by its ID
    @Override
    public String getTransferStatusById(int transferId) {
        String status = "";
        String sql = "SELECT transfer_status_desc FROM transfer_status WHERE transfer_status_id = ?";

        try {
            // Execute the query with a parameter and retrieve results
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
            if (results.next()) {
                // Map the row to a TenmoTransferStatus object and get the description
                status = mapRowToTenmoTransferStatus(results).getTransfer_status_desc();
            }
        } catch (CannotGetJdbcConnectionException e) {
            // Handle database connection exception
            throw new DaoException("Unable to connect to server or database", e);
        }
        return status;
    }

    // Maps a row of SQL results to a TenmoTransferStatus object
    private TenmoTransferStatus mapRowToTenmoTransferStatus(SqlRowSet results) {
        int transfer_status_id = results.getInt("transfer_status_id");
        String transfer_status_desc = results.getString("transfer_status_desc");

        return new TenmoTransferStatus(transfer_status_id, transfer_status_desc);
    }
}