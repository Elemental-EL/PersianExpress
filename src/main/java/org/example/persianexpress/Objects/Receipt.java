package org.example.persianexpress.Objects;

import java.util.Date;

public class Receipt extends Request{
    private int accID;
    private long chequeSerial;
    private long chequeAmount;
    private Date chequeDate;

    private Receipt(){
    }

    public Receipt(int reqID, int reqerID){
        setReqID(reqID);
        setReqerID(reqerID);
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public long getChequeSerial() {
        return chequeSerial;
    }

    public void setChequeSerial(long chequeSerial) {
        this.chequeSerial = chequeSerial;
    }

    public long getChequeAmount() {
        return chequeAmount;
    }

    public void setChequeAmount(long chequeAmount) {
        this.chequeAmount = chequeAmount;
    }
    public Date getChequeDate(){return chequeDate;}
    public void setChequeDate(Date chequeDate){this.chequeDate = chequeDate;}
}
