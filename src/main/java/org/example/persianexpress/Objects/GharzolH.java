package org.example.persianexpress.Objects;

import org.example.persianexpress.CustomersMoneyTransferController;
import org.example.persianexpress.HelloController;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Random;

public class GharzolH {
    private int accUID;
    private int accHolderUID;
    private String accNumber;
    private String accType;
    private long accBalance;
    private boolean accAccess;

    public GharzolH() {
    }

    public GharzolH(int accUID, int accHolderUID){
        this.accUID = accUID;
        this.accHolderUID = accHolderUID;
    }

    public int getAccUID() {
        return accUID;
    }

    public void setAccUID(int accUID) {
        this.accUID = accUID;
    }

    public int getAccHolderUID() {
        return accHolderUID;
    }

    public void setAccHolderUID(int accHolderUID) {
        this.accHolderUID = accHolderUID;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public long getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(long accBalance) {
        this.accBalance = accBalance;
    }

    public boolean isAccAccess() {
        return accAccess;
    }

    public void setAccAccess(boolean accAccess) {
        this.accAccess = accAccess;
    }

    public static String generateAccNum(Connection connection,String type) throws SQLException {
        boolean isNew=true;
        boolean canContinue=true;
        String accNum="";
        int prefix=1000;
        if (type.equals("قرض الحسنه جاری")){
            prefix = 3891;
        } else if (type.equals("قرض الحسنه سپرده")){
            prefix = 3892;
        } else if (type.equals("سپرده کوتاه مدت")){
            prefix = 3893;
        } else if (type.equals("سپرده بلند مدت")){
            prefix = 3894;
        }
        while (canContinue){
            isNew = true;
        Random random = new Random();
        accNum = String.valueOf(prefix * 10000 + random.nextInt(10000));
        PreparedStatement statement = connection.prepareStatement("SELECT AccountNumber FROM BankAccounts");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            if (accNum.equals(resultSet.getNString("AccountNumber"))){
                isNew = false;
            }
        }
        if (isNew) {
            canContinue = false;
        }
        }
        return accNum;
    }
    public static void createBankAcc(Connection connection, int uID, String accnum, String typeSlctd, LocalDate currentDate) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO BankAccounts (CustomerID,AccountNumber,AccountType,AccountStock,AccountProfit,AccountTerm,AccountAccess) VALUES (?,?,?,?,?,?,?)");
        statement.setInt(1, uID);
        statement.setString(2, accnum);
        statement.setString(3, typeSlctd);
        statement.setLong(4,10000000);
        if (typeSlctd.equals("سپرده کوتاه مدت")|| typeSlctd.equals("سپرده مدت دار")){
            if (typeSlctd.equals("سپرده کوتاه مدت")){
                statement.setInt(5,5);
                statement.setDate(6, java.sql.Date.valueOf(currentDate.plusYears(4)));
            }
            else {
                statement.setInt(5,23);
                statement.setDate(6, Date.valueOf(currentDate.plusYears(1)));
            }
        }
        else {
            statement.setInt(5,0);
            statement.setDate(6,null);
        }
        statement.setBoolean(7,true);
        int resultSet = statement.executeUpdate();
    }

    public static ResultSet getAvailableAccsForTransaction(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT AccountID , CustomerID , AccountNumber , AccountStock FROM BankAccounts WHERE (AccountType =N'قرض الحسنه جاری' OR AccountType =N'سپرده کوتاه مدت') AND CustomerID = ?");
        statement.setInt(1, HelloController.userID);
        return statement.executeQuery();
    }

    public static ResultSet getAvailableDestsForTransaction(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT AccountID , CustomerID , AccountNumber , AccountStock From BankAccounts WHERE CustomerID != ?");
        statement.setInt(1,HelloController.userID);
        return statement.executeQuery();
    }

    public String getHolderName(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT FirstName , LastName FROM CustomersInfo WHERE CustomerID = ?");
        statement.setInt(1,this.accHolderUID);
        ResultSet resultSet = statement.executeQuery();
        String holderName="";
        while (resultSet.next()){
            holderName = resultSet.getNString("FirstName");
            holderName+= " "+resultSet.getNString("LastName");
        }
        return holderName;
    }

    public void updateBalance(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE BankAccounts SET AccountStock = ? WHERE AccountID = ?");
        statement.setLong(1,this.accBalance);
        statement.setInt(2,this.accUID);
        int resultSet = statement.executeUpdate();
    }

    public static int submitTransaction(Sepordeh acc, String type, Connection connection, LocalDate nowsDate) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Transactions (CustomerID,TransactionType,SenderAccountNum,RecipientAccountNum,TransactionAmount,TransactionDate) VALUES (?,?,?,?,?,?)");
        statement.setInt(1, acc.getAccHolderUID());
        statement.setNString(2, type);
        statement.setNString(3, CustomersMoneyTransferController.orgAcc.getAccNumber());
        statement.setNString(4,CustomersMoneyTransferController.destAcc.getAccNumber());
        statement.setLong(5,CustomersMoneyTransferController.amount);
        statement.setDate(6, Date.valueOf(nowsDate));
        int resultSet = statement.executeUpdate();
        PreparedStatement statement1 = connection.prepareStatement("SELECT TOP 1 TransactionID FROM Transactions ORDER BY TransactionID DESC");
        ResultSet resultSet1 = statement1.executeQuery();
        int TID =0;
        while (resultSet1.next()){
            TID = resultSet1.getInt("TransactionID");
        }
        return TID;
    }
}
