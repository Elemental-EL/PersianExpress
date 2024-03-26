package org.example.persianexpress.Objects;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.persianexpress.HelloController;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class StockCheque {
    private int sCID;
    private int chequeHolderID;
    private int accID;
    private int sC;

    private StockCheque(){
    }

    public StockCheque(int sCID, int chequeHolderID, int accID){
        this.sCID = sCID;
        this.chequeHolderID = chequeHolderID;
        this.accID = accID;
    }

    public int getsCID() {
        return sCID;
    }

    public int getChequeHolderID() {
        return chequeHolderID;
    }

    public int getAccID() {
        return accID;
    }

    public int getsC() {
        return sC;
    }

    public void setsC(int sC) {
        this.sC = sC;
    }

    public static String[] getValidAccsWithCheque(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT AccountID FROM StockCheque WHERE CustomerID = ?");
        statement.setInt(1, HelloController.userID);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Integer> accIds = new ArrayList<>();
        while (resultSet.next()){
            accIds.add(resultSet.getInt("AccountID"));
        }
        String[] accs = new String[1000];
        accs[0] = "انتخاب کنید";
        int i=1;
        for (int accN : accIds){
            GharzolH acc = new GharzolH(accN,HelloController.userID);
            accs[i++] = acc.getAccountNumber(connection);
        }
        return Arrays.stream(accs).filter(Objects::nonNull).toArray(String[]::new);
    }

    public static ResultSet getValidUsersForIssuing(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT FirstName , LastName , NationalCode FROM CustomersInfo WHERE CustomerID != ?");
        statement.setInt(1,HelloController.userID);
        return statement.executeQuery();
    }

    public static long generateSerialNum (Connection connection) throws SQLException {
        Random random = new Random();
        boolean isNew=true;
        boolean canContinue=true;
        long cardNum=0;
        while (canContinue) {
            isNew = true;
            long cardNumR = random.nextLong(90000) + 10000;
            String prefix = "2077";
            cardNum = Long.parseLong(prefix+cardNumR);
            PreparedStatement statement0 = connection.prepareStatement("SELECT ChequeSerialNum FROM ChequeHistory");
            ResultSet resultSet0 = statement0.executeQuery();
            while (resultSet0.next()){
                if (cardNum == resultSet0.getLong("ChequeSerialNum")){
                    isNew = false;
                }
            }
            if (isNew) {
                canContinue = false;
            }
        }
        return cardNum;
    }

    public static boolean chequesLeft(Connection connection, int accountID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT StockCheque FROM StockCheque WHERE AccountID = ?");
        statement.setInt(1,accountID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            if (resultSet.getInt("StockCheque")==0) {
                return false;
            }
        }
        return true;
    }

    public static void issueCheque(String accSlctd, Connection connection, TextField nCodeText, TextField amountText, DatePicker date) throws SQLException {
        long serialNum = StockCheque.generateSerialNum(connection);
        PreparedStatement statement1 = connection.prepareStatement("INSERT INTO ChequeHistory (CustomerID,AccountID,RecipientID,ChequeSerialNum,ChequeAmount,ChequeDate,ChequeStatus) VALUES (?,?,?,?,?,?,?)");
        statement1.setInt(1,HelloController.userID);
        statement1.setInt(2,GharzolH.getAccountUID(accSlctd, connection));
        statement1.setInt(3, User.getUserID(connection, nCodeText.getText()));
        statement1.setLong(4,serialNum);
        statement1.setLong(5, Long.parseLong(amountText.getText()));
        statement1.setDate(6, Date.valueOf(date.getValue()));
        statement1.setBoolean(7,false);
        int resultSetN = statement1.executeUpdate();
        PreparedStatement statement2 = connection.prepareStatement("UPDATE StockCheque SET StockCheque = StockCheque - 1 WHERE AccountID = ?");
        statement2.setInt(1,GharzolH.getAccountUID(accSlctd, connection));
        int resN = statement2.executeUpdate();
    }

    public static ResultSet getUserCheques(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT ChequeID, ChequeSerialNum, ChequeAmount, ChequeDate FROM ChequeHistory WHERE RecipientID = ? AND ChequeStatus = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1,HelloController.userID);
        statement.setBoolean(2,false);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }
}
