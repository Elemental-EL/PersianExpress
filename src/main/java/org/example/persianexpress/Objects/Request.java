package org.example.persianexpress.Objects;

import java.util.Date;

public class Request {
    private int reqID;
    private int reqerID;
    private Date reqDate;
    private boolean reqStat;

    public int getReqID() {
        return reqID;
    }

    public void setReqID(int reqID) {
        this.reqID = reqID;
    }

    public int getReqerID() {
        return reqerID;
    }

    public void setReqerID(int reqerID) {
        this.reqerID = reqerID;
    }

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public boolean isReqStat() {
        return reqStat;
    }

    public void setReqStat(boolean reqStat) {
        this.reqStat = reqStat;
    }
}
