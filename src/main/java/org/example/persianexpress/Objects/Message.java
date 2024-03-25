package org.example.persianexpress.Objects;

import java.util.Date;

public class Message {
    private int messageID;
    private int senderUID;
    private int customerID;
    private String messageContext;
    private Date messageDate;
    private boolean messageStat;

    private Message(){
    }

    public Message(int messageID, int senderUID , int customerID) {
        this.messageID = messageID;
        this.senderUID = senderUID;
        this.customerID = customerID;
    }

    public int getMessageID() {
        return messageID;
    }

    public int getSenderUID() {
        return senderUID;
    }
    public void setSenderUID(int senderUID){
        this.senderUID = senderUID;
    }
    public int getCustomerID(){
        return customerID;
    }
    public void setCustomerID(int customerID){
        this.customerID = customerID;
    }
    public String getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public boolean isMessageStat() {
        return messageStat;
    }

    public void setMessageStat(boolean messageStat) {
        this.messageStat = messageStat;
    }
}
