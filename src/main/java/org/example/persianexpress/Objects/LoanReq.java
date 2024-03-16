package org.example.persianexpress.Objects;

import java.util.Date;

public class LoanReq extends Request{
    private int accID;
    private String cDiploma;
    private String cJob;
    private String sFName;
    private String sLName;
    private String sNCode;
    private String sFatherName;
    private String sBPlace;
    private Date sBDate;
    private long sPhNumber;
    private String sDiploma;
    private String sJob;
    private String loanType;

    private LoanReq(){
    }

    public LoanReq(int reqID, int reqerID){
        setReqID(reqID);
        setReqerID(reqerID);
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public String getcDiploma() {
        return cDiploma;
    }

    public void setcDiploma(String cDiploma) {
        this.cDiploma = cDiploma;
    }

    public String getcJob() {
        return cJob;
    }

    public void setcJob(String cJob) {
        this.cJob = cJob;
    }

    public String getsFName() {
        return sFName;
    }

    public void setsFName(String sFName) {
        this.sFName = sFName;
    }

    public String getsLName() {
        return sLName;
    }

    public void setsLName(String sLName) {
        this.sLName = sLName;
    }

    public String getsNCode() {
        return sNCode;
    }

    public void setsNCode(String sNCode) {
        this.sNCode = sNCode;
    }

    public String getsFatherName() {
        return sFatherName;
    }

    public void setsFatherName(String sFatherName) {
        this.sFatherName = sFatherName;
    }

    public String getsBPlace() {
        return sBPlace;
    }

    public void setsBPlace(String sBPlace) {
        this.sBPlace = sBPlace;
    }

    public Date getsBDate() {
        return sBDate;
    }

    public void setsBDate(Date sBDate) {
        this.sBDate = sBDate;
    }

    public long getsPhNumber() {
        return sPhNumber;
    }

    public void setsPhNumber(long sPhNumber) {
        this.sPhNumber = sPhNumber;
    }

    public String getsDiploma() {
        return sDiploma;
    }

    public void setsDiploma(String sDiploma) {
        this.sDiploma = sDiploma;
    }

    public String getsJob() {
        return sJob;
    }

    public void setsJob(String sJob) {
        this.sJob = sJob;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }
}
