package org.example.persianexpress;

public class User extends Person{
    private boolean access;

    private User(){
    }

    public User(int uID) {
        setuID(uID);
    }

    public boolean getAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }
}