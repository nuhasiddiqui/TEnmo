package com.techelevator.tenmo.datasource.model;

// This model represents the structure and attributes of the data stored in the transfer_status table
public class TenmoTransferStatus {

    // This TenmoTrasnferStatus.java class defines fields that correspond to the columns in the transfer_status table
    private int transfer_status_id;
    private String transfer_status_desc;

    // Constructor
    public TenmoTransferStatus(int transfer_status_id, String transfer_status_desc) {
        this.transfer_status_id = transfer_status_id;
        this.transfer_status_desc = transfer_status_desc;
    }

    // Getter and Setters: transfer_status_id and transfer_status_desc
    public int getTransfer_status_id() {
        return transfer_status_id;
    }

    public void setTransfer_status_id(int transfer_status_id) {
        this.transfer_status_id = transfer_status_id;
    }

    public String getTransfer_status_desc() {
        return transfer_status_desc;
    }

    public void setTransfer_status_desc(String transfer_status_desc) {
        this.transfer_status_desc = transfer_status_desc;
    }

    @Override
    public String toString() {
        return "TransferStatus{" +
                "transferStatusId=" + transfer_status_id +
                ", transferStatusDesc='" + transfer_status_desc + '\'' +
                '}';
    }
}