package org.example.persianexpress.Objects;

import java.util.Date;

public class GharzolH {
    private int accUID;
    private int accHolderUID;
    private long accNumber;
    private String accType;
    private long accBalance;
    private boolean accAccess;

    public GharzolH() {
    }

    public GharzolH(int accUID, int accHolderUID){
        this.accUID = accUID;
        this.accHolderUID = accHolderUID;
    }

    public int getAccUID() {
        return accUID;
    }

    public void setAccUID(int accUID) {
        this.accUID = accUID;
    }

    public int getAccHolderUID() {
        return accHolderUID;
    }

    public void setAccHolderUID(int accHolderUID) {
        this.accHolderUID = accHolderUID;
    }

    public long getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(long accNumber) {
        this.accNumber = accNumber;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public long getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(long accBalance) {
        this.accBalance = accBalance;
    }

    public boolean isAccAccess() {
        return accAccess;
    }

    public void setAccAccess(boolean accAccess) {
        this.accAccess = accAccess;
    }
}
