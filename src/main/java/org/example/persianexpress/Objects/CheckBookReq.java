package org.example.persianexpress.Objects;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.persianexpress.HelloController;

import java.sql.*;
import java.time.LocalDate;

public class CheckBookReq extends Request{
    private int accID;
    private int checkNum;

    private CheckBookReq(){
    }

    public CheckBookReq(int reqID, int reqerID){
        setReqID(reqID);
        setReqerID(reqerID);
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public int getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }

    public static void submitChequeBookReq(String accSlctd, String amountSlctd, Connection connection, User user, LocalDate nowsDate) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO CheckBookREQ (CustomerID,AccountID,CheckNum,RequestDate,RequestStatus) VALUES (?,?,?,?,?)");
        statement.setInt(1, user.getuID());
        statement.setInt(2,GharzolH.getAccountUID(accSlctd, connection));
        statement.setInt(3, Integer.parseInt(amountSlctd));
        statement.setDate(4, Date.valueOf(nowsDate));
        statement.setBoolean(5,false);
        int resultSet = statement.executeUpdate();
    }

    public static void createChequeStock(Connection connection, ResultSet resultSet) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO StockCheque (CustomerID,AccountID,StockCheque) VALUES (?,?,?)");
        statement.setInt(1, resultSet.getInt("CustomerID"));
        statement.setInt(2, resultSet.getInt("AccountID"));
        statement.setInt(3, resultSet.getInt("CheckNum"));
        int resultSetN = statement.executeUpdate();
    }

    public static void submitReceiptREQ(ResultSet resultSet, Connection connection, ChoiceBox<String> selectedAccount, TextField chequeSerialNumber) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO ReceiptChequeREQ (CustomerID,AccountID,RequestDate,ChequeSerialNum,ChequeDate,ChequeAmount,ReceiptChequeStatus) VALUES (?,?,?,?,?,?,?)");
        statement.setInt(1, HelloController.userID);
        statement.setInt(2,GharzolH.getAccountUID(selectedAccount.getSelectionModel().getSelectedItem(), connection));
        statement.setDate(3, Date.valueOf(LocalDate.now()));
        statement.setLong(4, Long.parseLong(chequeSerialNumber.getText()));
        statement.setDate(5, resultSet.getDate("ChequeDate"));
        statement.setLong(6, resultSet.getLong("ChequeAmount"));
        statement.setBoolean(7,false);
        int resN = statement.executeUpdate();
    }

    public static ResultSet getRequestedCheques(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT ChequeSerialNum FROM ReceiptChequeREQ WHERE CustomerID = ?");
        statement.setInt(1, HelloController.userID);
        ResultSet resultSet1 = statement.executeQuery();
        return resultSet1;
    }
}
