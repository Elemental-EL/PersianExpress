package org.example.persianexpress.Objects;

import javafx.scene.text.Text;
import org.example.persianexpress.Objects.GharzolH;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Sepordeh extends GharzolH {
    private int accInterest;
    private Date accTerminationDate;

    public Sepordeh(int accUID, int accHolderUID) {
        super(accUID, accHolderUID);
    }

    private Sepordeh() {

    }


    public int getAccInterest() {
        return accInterest;
    }

    public void setAccInterest(int accInterest) {
        this.accInterest = accInterest;
    }

    public Date getAccTerminationDate() {
        return accTerminationDate;
    }

    public void setAccTerminationDate(Date accTerminationDate) {
        this.accTerminationDate = accTerminationDate;
    }

    public static Sepordeh getAccForTransaction(String accNum, ResultSet resultSet) throws SQLException {
        boolean Valid=false;
        int accID=0,accHolder=0;
        long accBalance=0;
        while (resultSet.next()) {
            if (resultSet.getNString("AccountNumber").equals(accNum)) {
                Valid = true;
                accID = resultSet.getInt("AccountID");
                accHolder = resultSet.getInt("CustomerID");
                accBalance = resultSet.getLong("AccountStock");
            }
        }
            if (Valid) {
                Sepordeh Acc = new Sepordeh(accID, accHolder);
                Acc.setAccNumber(accNum);
                Acc.setAccBalance(accBalance);
                return Acc;
            } else {
            return null;
        }

    }

    public static boolean transactionIsValid(Sepordeh orgAcc, Sepordeh destAcc, Long amount, Text errorText) {
        if (orgAcc !=null&& destAcc !=null){
            if (orgAcc.getAccBalance()>= amount){
                return true;
            } else {
                errorText.setText("*موجودی شما کافی نمی باشد.");
                return false;
            }
        } else if (orgAcc ==null) {
            errorText.setText("*شماره حساب مبدا معتبر نمی باشد.");
            return false;
        } else {
            errorText.setText("شماره حساب مقصد معتبر نمی باشد.");
            return false;
        }
    }

}
