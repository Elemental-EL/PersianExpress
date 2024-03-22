package org.example.persianexpress.Objects;

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
}
