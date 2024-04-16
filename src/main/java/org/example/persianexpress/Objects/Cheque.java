package org.example.persianexpress.Objects;

import java.util.Date;

public class Cheque {
    private int chequeID;
    private int customerID;
    private int accID;
    private int recipientID;
    private long chequeSerialNum;
    private long amount;
    private Date chequeDate;
    private String status;

    private Cheque(){
    }

    public Cheque(int chequeID, int customerID, int accID) {
        this.chequeID = chequeID;
        this.customerID = customerID;
        this.accID = accID;
    }
    public int getChequeID() {
        return chequeID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getAccID() {
        return accID;
    }
    public int getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(int recipientID) {
        this.recipientID = recipientID;
    }

    public long getChequeSerialNum() {
        return chequeSerialNum;
    }

    public void setChequeSerialNum(long chequeSerialNum) {
        this.chequeSerialNum = chequeSerialNum;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Date getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(Date chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
