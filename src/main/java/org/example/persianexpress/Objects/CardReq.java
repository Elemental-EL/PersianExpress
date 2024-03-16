package org.example.persianexpress.Objects;

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
}
