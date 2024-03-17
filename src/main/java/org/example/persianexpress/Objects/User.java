package org.example.persianexpress.Objects;

import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.persianexpress.HelloController;
import org.example.persianexpress.Objects.Person;

import java.sql.*;

public class User extends Person {
    private boolean access;

    private User(){
    }

    public User(int uID) {
        setuID(uID);
    }

    public boolean getAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public static User createUserObj(Connection connection, int uID) throws SQLException {
        User user = new User(uID);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM CustomersInfo WHERE CustomerID = ?");
        statement.setInt(1,HelloController.userID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            user.setUserName(resultSet.getNString("CustomerUN"));
            user.setPassword(resultSet.getNString("CustomerPassword"));
            user.setfName(resultSet.getNString("FirstName"));
            user.setLastname(resultSet.getNString("LastName"));
            user.setNationalCode(resultSet.getString("NationalCode"));
            user.setFatherName(resultSet.getNString("FatherName"));
            user.setbDate(String.valueOf(resultSet.getDate("BirthDate")));
            user.setbPlace(resultSet.getNString("BirthPlace"));
            user.setPhNumber(resultSet.getString("PhoneNumber"));
            user.sethPhNumber(resultSet.getString("HomePhoneNumber"));
            user.sethAddress(resultSet.getNString("HomeAddress"));
            user.setpCode(resultSet.getString("PostCode"));
            user.setAccess(resultSet.getBoolean("Access"));
        }
        return user;
    }
    public static int createUser(Connection connection, TextField userText, PasswordField passText, TextField firstNameText, TextField familyNameText, TextField nCodeText, TextField fatherNameText, DatePicker bDate, TextField bPlaceText, TextField mPhoneText, TextField hPhoneText, TextArea addressText, TextField codePText) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO CustomersInfo (CustomerUN,CustomerPassword,FirstName,LastName,NationalCode,FatherName,BirthDate,BirthPlace,PhoneNumber,HomePhoneNumber,HomeAddress,PostCode,Access) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,true)");
        statement.setString(1, userText.getText());
        statement.setString(2, passText.getText());
        statement.setString(3, firstNameText.getText());
        statement.setString(4, familyNameText.getText());
        statement.setString(5, nCodeText.getText());
        statement.setString(6, fatherNameText.getText());
        statement.setDate(7, Date.valueOf(bDate.getValue()));
        statement.setString(8, bPlaceText.getText());
        statement.setString(9, mPhoneText.getText());
        statement.setString(10, hPhoneText.getText());
        statement.setString(11, addressText.getText());
        statement.setString(12, codePText.getText());
        int resultSet = statement.executeUpdate();
        int uID = 100;
        PreparedStatement statement1 = connection.prepareStatement("SELECT CustomerID FROM CustomerInfo WHERE CustomerUN=?");
        statement1.setString(1,userText.getText());
        ResultSet resultSet1 = statement1.executeQuery();
        while (resultSet1.next()){
            uID = resultSet1.getInt("CustomerID");
        }
        return uID;
    }
}
