package org.example.persianexpress.Objects;

import org.example.persianexpress.CustomersMoneyTransferController;
import org.example.persianexpress.HelloController;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

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
        } else if (type.equals("سپرده مدت دار")){
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
        PreparedStatement statement = connection.prepareStatement("SELECT AccountID , CustomerID , AccountNumber , AccountStock FROM BankAccounts WHERE (AccountType =N'قرض الحسنه جاری' OR AccountType =N'سپرده کوتاه مدت') AND CustomerID = ? AND AccountAccess = 1");
        statement.setInt(1, HelloController.userID);
        return statement.executeQuery();
    }

    public static ResultSet getAvailableDestsForTransaction(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT AccountID , CustomerID , AccountNumber , AccountStock From BankAccounts WHERE CustomerID != ? AND AccountAccess = 1");
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

    public String getAccountNumber (Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT AccountNumber FROM BankAccounts WHERE AccountID=?");
        statement.setInt(1,this.accUID);
        ResultSet resultSet = statement.executeQuery();
        String accNum="";
        while (resultSet.next()){
            accNum = resultSet.getNString("AccountNumber");
        }
        return accNum;
    }

    public static int getAccountUID(String accNumber,Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT AccountID FROM BankAccounts WHERE AccountNumber = ?");
        statement.setNString(1,accNumber);
        ResultSet resultSet = statement.executeQuery();
        int accID = 0;
        while (resultSet.next()){
            accID = resultSet.getInt("AccountID");
        }
        return accID;
    }

    public void updateBalance(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE BankAccounts SET AccountStock = ? WHERE AccountID = ?");
        statement.setLong(1,this.accBalance);
        statement.setInt(2,this.accUID);
        int resultSet = statement.executeUpdate();
    }

    public static void updateBalance(long newBalacne, ResultSet res, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE BankAccounts SET AccountStock = ? WHERE AccountID = ?");
        statement.setLong(1, newBalacne);
        statement.setInt(2, res.getInt("AccountID"));
        int resN = statement.executeUpdate();
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

    public static String[] getAccNumsForReceipt(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT AccountNumber FROM BankAccounts WHERE CustomerID = ? AND (AccountType=N'قرض الحسنه جاری' OR AccountType=N'سپرده کوتاه مدت')");
        statement.setInt(1,HelloController.userID);
        ResultSet resultSet = statement.executeQuery();
        String[] accs = new String[1000];
        accs[0] = "انتخاب کنید";
        int i =1;
        while (resultSet.next()){
            accs[i++] = resultSet.getNString("AccountNumber");
        }
        return Arrays.stream(accs).filter(Objects::nonNull).toArray(String[]::new);
    }

    public static String[] getValidAccNumsForCheque(Connection connection) throws SQLException {
        PreparedStatement statement1 = connection.prepareStatement("SELECT AccountID FROM StockCheque WHERE CustomerID = ?");
        statement1.setInt(1,HelloController.userID);
        ResultSet resultSet1 = statement1.executeQuery();
        ArrayList<Integer> illegalAccs = new ArrayList<>();
        while (resultSet1.next()){
            illegalAccs.add(resultSet1.getInt("AccountID"));
        }
        PreparedStatement statement2 = connection.prepareStatement("SELECT AccountID FROM CheckBookREQ WHERE CustomerID = ?");
        statement2.setInt(1,HelloController.userID);
        ResultSet resultSet2 = statement2.executeQuery();
        while (resultSet2.next()){
            illegalAccs.add(resultSet2.getInt("AccountID"));
        }
        PreparedStatement statement3 = connection.prepareStatement("SELECT AccountID FROM BankAccounts WHERE CustomerID = ? AND AccountType =N'قرض الحسنه جاری' AND AccountAccess = 1");
        statement3.setInt(1,HelloController.userID);
        ResultSet resultSet3 = statement3.executeQuery();
        ArrayList<Integer> availableAccs = new ArrayList<>();
        while (resultSet3.next()){
            availableAccs.add(resultSet3.getInt("AccountID"));
        }
        ArrayList<Integer> validAccs = new ArrayList<>();
        for (int acc : availableAccs){
            if (!illegalAccs.contains(acc)){
                validAccs.add(acc);
            }
        }
        String[] accs = new String[1000];
        accs[0] = "انتخاب کنید";
        int i=1;
        for (int accN : validAccs){
            GharzolH acc = new GharzolH(accN,HelloController.userID);
            accs[i++] = acc.getAccountNumber(connection);
        }
        return Arrays.stream(accs).filter(Objects::nonNull).toArray(String[]::new);
    }

    public static String[] getValidAccNumsForCard(Connection connection) throws SQLException {
        PreparedStatement statement1 = connection.prepareStatement("SELECT AccountID FROM BankCards WHERE CustomerID = ?");
        statement1.setInt(1,HelloController.userID);
        ResultSet resultSet1 = statement1.executeQuery();
        ArrayList<Integer> illegalAccs = new ArrayList<>();
        while (resultSet1.next()){
            illegalAccs.add(resultSet1.getInt("AccountID"));
        }
        PreparedStatement statement2 = connection.prepareStatement("SELECT SelectedAccountID FROM BankCardREQ WHERE CustomerID = ?");
        statement2.setInt(1,HelloController.userID);
        ResultSet resultSet2 = statement2.executeQuery();
        while (resultSet2.next()){
            illegalAccs.add(resultSet2.getInt("SelectedAccountID"));
        }
        PreparedStatement statement3 = connection.prepareStatement("SELECT AccountID FROM BankAccounts WHERE CustomerID = ? AND (AccountType=N'قرض الحسنه جاری' OR AccountType=N'سپرده کوتاه مدت') AND AccountAccess = 1");
        statement3.setInt(1,HelloController.userID);
        ResultSet resultSet3 = statement3.executeQuery();
        ArrayList<Integer> availableAccs = new ArrayList<>();
        while (resultSet3.next()){
            availableAccs.add(resultSet3.getInt("AccountID"));
        }
        ArrayList<Integer> validAccs = new ArrayList<>();
        for (int acc : availableAccs){
            if (!illegalAccs.contains(acc)){
                validAccs.add(acc);
            }
        }
        String[] accs = new String[1000];
        accs[0] = "انتخاب کنید";
        int i=1;
        for (int accN : validAccs){
            GharzolH acc = new GharzolH(accN,HelloController.userID);
            accs[i++] = acc.getAccountNumber(connection);
        }
        return Arrays.stream(accs).filter(Objects::nonNull).toArray(String[]::new);
    }

    public static Long getAccountBalance(String accSlctd, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT AccountStock FROM BankAccounts WHERE AccountNumber = ?");
        statement.setNString(1, accSlctd);
        ResultSet resultSet1 = statement.executeQuery();
        resultSet1.next();
        return resultSet1.getLong("AccountStock");
    }

    public static Long getAccountBalance(int accID, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT AccountStock FROM BankAccounts WHERE AccountID = ?");
        statement.setInt(1, accID);
        ResultSet resultSet1 = statement.executeQuery();
        resultSet1.next();
        return resultSet1.getLong("AccountStock");
    }

    public static void suspendAccount(int accID, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE BankAccounts SET AccountAccess = 0 WHERE AccountID=?");
        statement.setInt(1, accID);
        int resN = statement.executeUpdate();
    }

}
