package org.example.persianexpress.Objects;

import java.util.Date;

public class Message {
    private int messageID;
    private int senderUID;
    private String messageContext;
    private Date messageDate;
    private boolean messageStat;

    private Message(){
    }

    public Message(int messageID, int senderUID) {
        this.messageID = messageID;
        this.senderUID = senderUID;
    }

    public int getMessageID() {
        return messageID;
    }

    public int getSenderUID() {
        return senderUID;
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
