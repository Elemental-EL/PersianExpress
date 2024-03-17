package org.example.persianexpress.Objects;

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
        switch (type){
            case "قرض الحسنه جاری":
                prefix = 3891;
                break;
            case "قرض الحسنه سپرده":
                prefix = 3892;
                break;
            case "سپرده کوتاه مدت":
                prefix = 3893;
                break;
            case "سپرده بلند مدت":
                prefix = 3894;
                break;
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
        statement.setBoolean(7,true);
        int resultSet = statement.executeUpdate();
    }
}
