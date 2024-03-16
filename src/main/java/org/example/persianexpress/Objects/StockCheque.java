package org.example.persianexpress.Objects;

public class StockCheque {
    private int sCID;
    private int chequeHolderID;
    private int accID;
    private int sC;

    private StockCheque(){
    }

    public StockCheque(int sCID, int chequeHolderID, int accID){
        this.sCID = sCID;
        this.chequeHolderID = chequeHolderID;
        this.accID = accID;
    }

    public int getsCID() {
        return sCID;
    }

    public int getChequeHolderID() {
        return chequeHolderID;
    }

    public int getAccID() {
        return accID;
    }

    public int getsC() {
        return sC;
    }

    public void setsC(int sC) {
        this.sC = sC;
    }
}
