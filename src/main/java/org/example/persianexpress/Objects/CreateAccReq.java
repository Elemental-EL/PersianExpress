package org.example.persianexpress.Objects;

import java.util.Date;

public class CreateAccReq extends Request{
    private String accType;
    private String uName;
    private String pass;
    private String fName;
    private String lName;
    private long nCode;
    private Date bDate;
    private String bPlace;
    private long phNumber;
    private long hPhNumber;
    private String hAdress;
    private long pCode;

    private CreateAccReq(){
    }

    public CreateAccReq(int reqID, int reqerID){
        setReqID(reqID);
        setReqerID(reqerID);
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public long getnCode() {
        return nCode;
    }

    public void setnCode(long nCode) {
        this.nCode = nCode;
    }

    public Date getbDate() {
        return bDate;
    }

    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

    public String getbPlace() {
        return bPlace;
    }

    public void setbPlace(String bPlace) {
        this.bPlace = bPlace;
    }

    public long getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(long phNumber) {
        this.phNumber = phNumber;
    }

    public long gethPhNumber() {
        return hPhNumber;
    }

    public void sethPhNumber(long hPhNumber) {
        this.hPhNumber = hPhNumber;
    }

    public String gethAdress() {
        return hAdress;
    }

    public void sethAdress(String hAdress) {
        this.hAdress = hAdress;
    }

    public long getpCode() {
        return pCode;
    }

    public void setpCode(long pCode) {
        this.pCode = pCode;
    }
}
