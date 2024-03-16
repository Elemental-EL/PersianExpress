package org.example.persianexpress.Objects;

public class DeleteAccReq extends Request{
    private int accID;
    private long substituteAccID;

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

    public long getSubstituteAccID() {
        return substituteAccID;
    }

    public void setSubstituteAccID(long substituteAccID) {
        this.substituteAccID = substituteAccID;
    }
}
