package org.example.persianexpress.Objects;

import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class LoanReq extends Request{
    private int accID;
    private String sFName;
    private String sLName;
    private String sNCode;
    private String sFatherName;
    private String sBPlace;
    private Date sBDate;
    private String sPhNumber;
    private String loanType;
    private String loanText;

    private LoanReq(){
    }

    public LoanReq(int reqID, int reqerID){
        setReqID(reqID);
        setReqerID(reqerID);
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public String getsFName() {
        return sFName;
    }

    public void setsFName(String sFName) {
        this.sFName = sFName;
    }

    public String getsLName() {
        return sLName;
    }

    public void setsLName(String sLName) {
        this.sLName = sLName;
    }

    public String getsNCode() {
        return sNCode;
    }

    public void setsNCode(String sNCode) {
        this.sNCode = sNCode;
    }

    public String getsFatherName() {
        return sFatherName;
    }

    public void setsFatherName(String sFatherName) {
        this.sFatherName = sFatherName;
    }

    public String getsBPlace() {
        return sBPlace;
    }

    public void setsBPlace(String sBPlace) {
        this.sBPlace = sBPlace;
    }

    public Date getsBDate() {
        return sBDate;
    }

    public void setsBDate(Date sBDate) {
        this.sBDate = sBDate;
    }

    public String getsPhNumber() {
        return sPhNumber;
    }

    public void setsPhNumber(String sPhNumber) {
        this.sPhNumber = sPhNumber;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanText() {
        return loanText;
    }

    public void setLoanText(String loanText) {
        this.loanText = loanText;
    }

    public static void submitLoanReq(String accNum, User surety, String lType, Connection connection, User user, TextArea applicantTXT) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO LoanREQ (CustomerID,SelectedAccountID,RequestText,SuretyFirstName,SuretyLastName,SuretyNationalCode,SuretyFatherName,SuretyBirthPlace,SuretyBirthDate,SuretyPhoneNumber,LoanType,RequestDate,RequestStatus) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
        statement.setInt(1, user.getuID());
        statement.setInt(2,GharzolH.getAccountUID(accNum, connection));
        statement.setNString(3, applicantTXT.getText());
        statement.setNString(4, surety.getfName());
        statement.setNString(5, surety.getLastname());
        statement.setString(6, surety.getNationalCode());
        statement.setNString(7, surety.getFatherName());
        statement.setNString(8, surety.getbPlace());
        statement.setDate(9, java.sql.Date.valueOf(surety.getbDate()));
        statement.setString(10, surety.getPhNumber());
        statement.setNString(11, lType);
        statement.setDate(12, java.sql.Date.valueOf(LocalDate.now()));
        statement.setBoolean(13,false);
        int res = statement.executeUpdate();
    }
     public static long getLoanAmount(String loanType){
        long amount = 0;
        switch (loanType) {
            case "وام دانشجویی":
                amount = 50000000;
                break;
            case "وام قرض الحسنه":
                amount = 200000000;
                break;
            case "وام ازدواج":
                amount = 1000000000;
                break;
            case "وام مسکن":
                amount = 3000000000L;
                break;
        }
        return amount;
     }
}
