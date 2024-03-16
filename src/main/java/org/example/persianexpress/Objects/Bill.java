package org.example.persianexpress.Objects;

import java.util.Date;

public class Bill {
    private int payingID;
    private int billHolderID;
    private int relAccID;
    private long billID;
    private long payID;
    private long billAmount;
    private Date paymentDate;

    private Bill(){
    }

    public Bill(int payingID, int billHolderID, int relAccID) {
        this.payingID = payingID;
        this.billHolderID = billHolderID;
        this.relAccID = relAccID;
    }

    public int getPayingID() {
        return payingID;
    }

    public int getBillHolderID() {
        return billHolderID;
    }

    public int getRelAccID() {
        return relAccID;
    }

    public long getBillID() {
        return billID;
    }

    public void setBillID(long billID) {
        this.billID = billID;
    }

    public long getPayID() {
        return payID;
    }

    public void setPayID(long payID) {
        this.payID = payID;
    }

    public long getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(long billAmount) {
        this.billAmount = billAmount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
