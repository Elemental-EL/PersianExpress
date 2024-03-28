package org.example.persianexpress.Objects;

import java.util.Date;

public class Card {
    private int cardID;
    private int cardHolderUID;
    private int relAccID;
    private String cardNumber;
    private String cvv2;
    private Date cardExpDate;
    private boolean access;

    private Card(){
    }

    public Card(int cardID, int cardHolderUID, int relAccID) {
        this.cardID = cardID;
        this.cardHolderUID = cardHolderUID;
        this.relAccID = relAccID;
    }
    public int getRelAccID(){
        return relAccID;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public Date getCardExpDate() {
        return cardExpDate;
    }

    public void setCardExpDate(Date cardExpDate) {
        this.cardExpDate = cardExpDate;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }
}
