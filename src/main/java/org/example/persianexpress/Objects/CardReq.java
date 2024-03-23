package org.example.persianexpress.Objects;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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
}
