package org.example.persianexpress.Objects;

import java.util.Date;

public class Transactions {
    private int transactionID;
    private int customerID;
    private String transactionType;
    private String senderAccNum;
    private String recipientAccNum;
    private long transAmount;
    private Date transDate;
    public Transactions(int transactionID){
        this.transactionID = transactionID;
    }
    public int getTransactionID(){
        return transactionID;
    }
    public void setCustomerID(int customerID){
        this.customerID = customerID;
    }
    public int getCustomerID(){
        return customerID;
    }
    public void setTransactionType(String transactionType){
        this.transactionType = transactionType;
    }
    public String getTransactionType(){
        return transactionType;
    }
    public void setSenderAccNum(String senderAccNum){
        this.senderAccNum = senderAccNum;
    }
    public String getSenderAccNum(){
        return senderAccNum;
    }
    public void setRecipientAccNum(String recipientAccNum){
        this.recipientAccNum = recipientAccNum;
    }
    public String getRecipientAccNum(){
        return recipientAccNum;
    }
    public void setTransAmount(long transAmount){
        this.transAmount = transAmount;
    }
    public long getTransAmount(){
        return transAmount;
    }
    public void setTransDate(Date transDate){
        this.transDate = transDate;
    }
    public Date getTransDate(){
        return transDate;
    }
}
