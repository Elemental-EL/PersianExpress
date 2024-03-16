package org.example.persianexpress;

public class Employee extends Person{
    private String employeePost;
    private boolean access;

    private Employee(){
    }

    public Employee(int uID) {
        setuID(uID);
    }

    public String getEmployeePost() {
        return employeePost;
    }

    public void setEmployeePost(String employeePost) {
        this.employeePost = employeePost;
    }
}
