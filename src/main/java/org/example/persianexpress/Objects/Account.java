package org.example.persianexpress.Objects;

public class Account {
    private int AccountID;
    private int CustomerID;
    private String AccountNumber;
    private String AccountType;
    private long AccountStock;
    private boolean AccountAccess;
    public Account(int AccountID){
        this.AccountID = AccountID;
    }
    public int getAccountID(){
        return AccountID;
    }
    public void setCustomerID(int CustomerID){
        this.CustomerID = CustomerID;
    }
    public int getCustomerID(){
        return CustomerID;
    }
    public void setAccountNumber(String AccountNumber){
        this.AccountNumber = AccountNumber;
    }
    public String getAccountNumber(){
        return AccountNumber;
    }
    public void setAccountType(String AccountType){
        this.AccountType = AccountType;
    }
    public String getAccountType(){
        return AccountType;
    }
    public void setAccountStock(long AccountStock){
        this.AccountStock = AccountStock;
    }
    public long getAccountStock(){
        return AccountStock;
    }
    public void setAccountAccess(boolean AccountAccess){
        this.AccountAccess = AccountAccess;
    }
    public boolean isAccountAccess(){
        return AccountAccess;
    }

}
