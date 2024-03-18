package org.example.persianexpress.Objects;

import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class CreateAccReq extends Request{
    private String accType;
    private String uName;
    private String pass;
    private String fName;
    private String lName;
    private String nCode;
    private Date bDate;
    private String bPlace;
    private String phNumber;
    private String hPhNumber;
    private String hAdress;
    private long pCode;

    private CreateAccReq(){
    }

    public CreateAccReq(int reqID, int reqerID){
        setReqID(reqID);
        setReqerID(reqerID);
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getnCode() {
        return nCode;
    }

    public void setnCode(String nCode) {
        this.nCode = nCode;
    }

    public Date getbDate() {
        return bDate;
    }

    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

    public String getbPlace() {
        return bPlace;
    }

    public void setbPlace(String bPlace) {
        this.bPlace = bPlace;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String gethPhNumber() {
        return hPhNumber;
    }

    public void sethPhNumber(String hPhNumber) {
        this.hPhNumber = hPhNumber;
    }

    public String gethAdress() {
        return hAdress;
    }

    public void sethAdress(String hAdress) {
        this.hAdress = hAdress;
    }

    public long getpCode() {
        return pCode;
    }

    public void setpCode(long pCode) {
        this.pCode = pCode;
    }

    public static void insert2DB(Connection connection, java.sql.Date currentDate, ChoiceBox<String> typeSlct, TextField userText, PasswordField passText, TextField firstNameText, TextField familyNameText, TextField nCodeText, DatePicker bDate, TextField bPlaceText, TextField mPhoneText, TextField hPhoneText, TextArea addressText, TextField codePText, TextField fatherNameText, int CustomerID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO CreateAccountREQ (AccountType,CustomerUN,CustomerPassword,FirstName,LastName,NationalCode,FatherName,BirthDate,BirthPlace,PhoneNumber,HomePhoneNumber,HomeAddress,PostCode,RequestDate,RequestStatus,CustomerID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        statement.setString(1, typeSlct.getValue());
        statement.setString(2, userText.getText());
        statement.setString(3, passText.getText());
        statement.setString(4, firstNameText.getText());
        statement.setString(5, familyNameText.getText());
        statement.setString(6, nCodeText.getText());
        statement.setString(7, fatherNameText.getText());
        statement.setDate(8, java.sql.Date.valueOf(bDate.getValue()));
        statement.setString(9, bPlaceText.getText());
        statement.setString(10, mPhoneText.getText());
        statement.setString(11, hPhoneText.getText());
        statement.setString(12, addressText.getText());
        statement.setString(13, codePText.getText());
        statement.setDate(14, currentDate);
        statement.setBoolean(15 ,false);
        statement.setInt(16, CustomerID);
        int resultSet = statement.executeUpdate();
    }
}
