package com.techelevator.tenmo.datasource.dao;

import com.techelevator.tenmo.datasource.model.TenmoUser;

public interface TenmoUserDao {
    TenmoUser getUsernameById(int usernameId);
}
