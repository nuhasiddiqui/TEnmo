package com.techelevator.tenmo.datasource.JdbcDao;

import com.techelevator.tenmo.datasource.dao.TenmoUserDao;
import com.techelevator.tenmo.datasource.model.TenmoUser;
import com.techelevator.tenmo.exception.DaoException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcTenmoUserDao implements TenmoUserDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTenmoUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Retrieve all usernames from the database
    public List<TenmoUser> getAllUsernames() {
        List<TenmoUser> users = new ArrayList<>();
        String sql = "SELECT username, user_id FROM tenmo_user;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                TenmoUser user = mapRowToTenmoUser(results);
                users.add(user);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or database", e);
        }
        return users;
    }

    // Retrieve a username by user ID
    @Override
    public TenmoUser getUsernameById(int userId) {
        TenmoUser user = null;
        String sql = "SELECT username, user_id FROM tenmo_user WHERE user_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                user = mapRowToTenmoUser(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or database", e);
        }
        return user;
    }

    // Map a database row to a TenmoUser object
    private TenmoUser mapRowToTenmoUser(SqlRowSet results) {
        int user_id = results.getInt("user_id");
        String username = results.getString("username");
        String password_hash = results.getString("password_hash");
        String role = results.getString("role");
        Timestamp created_at = results.getTimestamp("created_at");

        return new TenmoUser(user_id, username, password_hash, role, created_at);
    }

}
