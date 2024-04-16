package org.example.persianexpress.Objects;

import java.util.Date;

public class Loan {

    private int loanID;
    private int customerID;
    private int accID;
    private String loanType;
    private long loanAmount;
    private Date loanDate;
    private int loanInstalments;
    private long remainingAmount;

    private Loan(){

    }
    public Loan(int loanID , int customerID){
        this.loanID = loanID;
        this.customerID = customerID;
    }
    public int getLoanID(){
        return loanID;
    }
    public int getCustomerID(){
        return customerID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }
    public int getAccID(){
        return accID;
    }
    public void setLoanType(String loanType){
        this.loanType = loanType;
    }
    public String getLoanType(){
        return loanType;
    }
    public void setLoanAmount(long loanAmount){
        this.loanAmount = loanAmount;
    }

    public long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanDate(Date loanDate){
        this.loanDate = loanDate;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanInstalments(int loanInstalments) {
        this.loanInstalments = loanInstalments;
    }

    public int getLoanInstalments() {
        return loanInstalments;
    }

    public void setRemainingAmount(long remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public long getRemainingAmount() {
        return remainingAmount;
    }
}
