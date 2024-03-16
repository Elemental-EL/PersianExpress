package org.example.persianexpress.Objects;

public class CheckBookReq extends Request{
    private int accID;
    private int checkNum;

    private CheckBookReq(){
    }

    public CheckBookReq(int reqID, int reqerID){
        setReqID(reqID);
        setReqerID(reqerID);
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public int getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }
}
