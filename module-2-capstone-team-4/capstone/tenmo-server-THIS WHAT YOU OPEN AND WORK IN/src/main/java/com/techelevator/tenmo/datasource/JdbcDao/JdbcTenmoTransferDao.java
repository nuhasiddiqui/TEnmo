package com.techelevator.tenmo.datasource.JdbcDao;

import com.techelevator.tenmo.datasource.dao.TenmoTransferDao;
import com.techelevator.tenmo.datasource.model.TenmoTransfer;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcTenmoTransferDao implements TenmoTransferDao {

    @Override
    public TenmoTransfer saveTransfer(TenmoTransfer aTransfer) {

        // TODO: Given a Transfer object write a method to get add the Transfer to the data source

        return null;
    }

    @Override
    public List<TenmoTransfer> getTransfersForUser(int userId) {

        // TODO: Given a user id, write a method to retrieve all transfers for that user id from the data source

        return null;
    }

    @Override
    public TenmoTransfer getATransferById(Long transferIdRequested) {

        // TODO: Given a transfer id, retrieve the Transfer from the data source

        return null;
    }

}
