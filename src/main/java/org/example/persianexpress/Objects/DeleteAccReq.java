package org.example.persianexpress.Objects;

public class DeleteAccReq extends Request{
    private int accID;
    private String substituteAccID;

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
        return substituteAccID;
    }

    public void setSubstituteAccNum(String substituteAccID) {
        this.substituteAccID = substituteAccID;
    }
}
