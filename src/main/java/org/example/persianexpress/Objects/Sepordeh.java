package org.example.persianexpress.Objects;

import org.example.persianexpress.Objects.GharzolH;

import java.util.Date;

public class Sepordeh extends GharzolH {
    private int accInterest;
    private Date accTerminationDate;

    public Sepordeh(int accUID, int accHolderUID) {
        super(accUID, accHolderUID);
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

}
