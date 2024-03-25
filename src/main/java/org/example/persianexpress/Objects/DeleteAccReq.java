package org.example.persianexpress.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteAccReq extends Request{
    private int accID;
    private String substituteAccNum;

    private DeleteAccReq(){
    }

    public DeleteAccReq(int reqID, int reqerID){
        setReqID(reqID);
        setReqerID(reqerID);
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public String getSubstituteAccNum() {
        return substituteAccNum;
    }

    public void setSubstituteAccNum(String substituteAccNum) {
        this.substituteAccNum = substituteAccNum;
    }

    public static void deleteAccount(Connection connection, ResultSet resultSet) throws SQLException {
        PreparedStatement statement1 = connection.prepareStatement("SELECT AccountStock FROM BankAccounts WHERE AccountID = ?");
        statement1.setInt(1,resultSet.getInt("AccountID"));
        ResultSet resultSet1 = statement1.executeQuery();
        long balance=0;
        while (resultSet1.next()) {
            balance = resultSet1.getLong("AccountStock");
        }
        PreparedStatement statement2 = connection.prepareStatement("SELECT AccountStock FROM BankAccounts WHERE AccountNumber = ?");
        statement2.setNString(1,resultSet.getNString("SubstituteAccount"));
        ResultSet resultSet2 = statement2.executeQuery();
        long newBalance = balance;
        while (resultSet2.next()){
            newBalance+=resultSet2.getLong("AccountStock");
        }
        PreparedStatement statement3 = connection.prepareStatement("UPDATE BankAccounts SET AccountStock = ? WHERE AccountNumber = ?");
        statement3.setLong(1,newBalance);
        statement3.setNString(2,resultSet.getNString("SubstituteAccount"));
        int res1 = statement3.executeUpdate();
        PreparedStatement statement4 = connection.prepareStatement("DELETE FROM BankAccounts WHERE AccountID = ?");
        statement4.setInt(1,resultSet.getInt("AccountID"));
        int res2 = statement4.executeUpdate();
        PreparedStatement statement5 = connection.prepareStatement("DELETE FROM BankCards WHERE AccountID = ?");
        statement5.setInt(1,resultSet.getInt("AccountID"));
        int res3 = statement5.executeUpdate();
        PreparedStatement statement6 = connection.prepareStatement("DELETE FROM StockCheque WHERE AccountID = ?");
        statement6.setInt(1,resultSet.getInt("AccountID"));
        int res4 = statement6.executeUpdate();
    }
}
