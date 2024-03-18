package org.example.persianexpress.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Request {
    private int reqID;
    private int reqerID;
    private Date reqDate;
    private boolean reqStat;

    public int getReqID() {
        return reqID;
    }

    public void setReqID(int reqID) {
        this.reqID = reqID;
    }

    public int getReqerID() {
        return reqerID;
    }

    public void setReqerID(int reqerID) {
        this.reqerID = reqerID;
    }

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public boolean isReqStat() {
        return reqStat;
    }

    public void setReqStat(boolean reqStat) {
        this.reqStat = reqStat;
    }

    public static ResultSet getReq(Connection connection,int reqID) throws SQLException {
        if (reqID<100000 || reqID>999999){
            return null;
        }
        else {
            String query=null;
            if (reqID/100000==1){
                query = "select * from CreateAccountREQ where RequestID = ?";
            } else if (reqID/100000==2) {
                query = "select * from DeleteAccountREQ where RequestID = ?";
            } else if (reqID/100000==3) {
                query = "select * from BankCardREQ where RequestID = ?";
            } else if (reqID/100000==4) {
                query = "select * from LoanREQ where RequestID = ?";
            } else if (reqID/100000==5) {
               query = "select * from CheckBookREQ where RequestID = ?";
            } else if (reqID/100000==6) {
               query = "select * from ReceiptChequeREQ where RequestID = ?";
            }
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,reqID);
            return statement.executeQuery();
        }
    }
    public static String getReqType(int reqID){
        if (reqID<100000 || reqID>999999){
            return null;
        }
        else {
            switch (reqID / 100000) {
                case 1:
                    return "CreateAccountREQ";
                case 2:
                    return "DeleteAccountREQ";
                case 3:
                    return "BankCardREQ";
                case 4:
                    return "LoanREQ";
                case 5:
                    return "CheckBookREQ";
                case 6:
                    return "ReceiptChequeREQ";
                default:
                    return null;
            }
        }
    }

    public static void passToHistory(Connection connection, ResultSet resultSet, boolean isAccepted) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO RequestsHistory VALUES (?,?,?,?)");
        statement.setInt(1, resultSet.getInt("RequestID"));
        statement.setInt(2, resultSet.getInt("CustomerID"));
        statement.setDate(3, resultSet.getDate("RequestDate"));
        statement.setBoolean(4, isAccepted);
        int resultSetN = statement.executeUpdate();
    }

    public static void deleteFromCreateAccountREQ(Connection connection, ResultSet resultSet) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM CreateAccountReq WHERE RequestID = ?");
        statement.setInt(1, resultSet.getInt("RequestID"));
        int resultSetN = statement.executeUpdate();
    }
}
