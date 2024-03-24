package org.example.persianexpress.Objects;

import java.sql.*;
import java.time.LocalDate;
import java.util.Random;

public class CardReq extends Request{
    private int accID;
    private CardReq(){
    }

    public CardReq(int reqID, int reqerID){
        setReqID(reqID);
        setReqerID(reqerID);
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public static void submitCardReq (String accSlctd, Connection connection, User user, LocalDate nowsDate) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO BankCardREQ (CustomerId,SelectedAccountID,RequestDate,RequestStatus) VALUES (?,?,?,?)");
        statement.setInt(1, user.getuID());
        statement.setInt(2,GharzolH.getAccountUID(accSlctd, connection));
        statement.setDate(3, Date.valueOf(nowsDate));
        statement.setBoolean(4,false);
        int resultSet = statement.executeUpdate();
    }

    public static void createCard (Connection connection, ResultSet resultSet) throws SQLException {
        Random random = new Random();
        int cvv2 = random.nextInt(900) + 100;
        Date expiration = Date.valueOf(LocalDate.now().plusYears(4));
        boolean isNew=true;
        boolean canContinue=true;
        String cardNum="";
        while (canContinue) {
            isNew = true;
            long cardNumR = random.nextLong(90000000) + 10000000;
            cardNum = "63948384" + cardNumR;
            PreparedStatement statement0 = connection.prepareStatement("SELECT CardNumber FROM BankCards");
            ResultSet resultSet0 = statement0.executeQuery();
            while (resultSet0.next()){
                if (cardNum.equals(resultSet0.getNString("CardNumber"))){
                    isNew = false;
                }
            }
            if (isNew) {
                canContinue = false;
            }
        }
        PreparedStatement statement = connection.prepareStatement("INSERT INTO BankCards (CustomerID , AccountID , CardNumber , CVV2 , CardTerm , CardAccess) VALUES (?,?,?,?,?,?)");
        statement.setInt(1,resultSet.getInt("CustomerId"));
        statement.setInt(2,resultSet.getInt("SelectedAccountID"));
        statement.setNString(3,cardNum);
        statement.setNString(4, String.valueOf(cvv2));
        statement.setDate(5,expiration);
        statement.setBoolean(6,true);
        int res = statement.executeUpdate();
    }
}
