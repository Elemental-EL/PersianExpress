package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.Account;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class CustomersRequestLoanController {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField applicantFirstName , applicantFamily , applicantNCode , applicantFatherName , applicantBPlace , applicantPhoneNumber , suretyFirstName , suretyFamily , suretyNCode , suretyFatherName , suretyBPlace , suretyPhoneNumber , LoanAmount;
    @FXML
    private DatePicker applicantBDate , suretyBDate ;
    @FXML
    private TextArea applicantTXT ;
    @FXML
    private ChoiceBox<String> loanType , selectedAccount;

    private Connection connection;
    private PreparedStatement statement;
    private ArrayList<Account> accounts = new ArrayList<>();

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select *from BankAccounts where CustomerID = ?");
        statement.setInt(1 , HelloController.userID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            if (resultSet.getLong("AccountStock") >= 25000000 && resultSet.getBoolean("AccountAccess")){
                Account account = new Account(resultSet.getInt("AccountID"));
                account.setAccountNumber(resultSet.getNString("AccountNumber"));
                account.setAccountStock(resultSet.getLong("AccountStock"));
                accounts.add(account);
            }
        }
        selectedAccount.getItems().add("انتخاب کنید");
        selectedAccount.setValue("انتخاب کنید");
        if (accounts.size() != 0) {
            for (Account account:accounts) {
                selectedAccount.getItems().add(account.getAccountNumber());
            }
            selectedAccount.setOnAction(event -> {
                int index = -1;
                long stock = 0;
                for ( int i = 0 ; i < accounts.size() ; i++){
                    if (Objects.equals(accounts.get(i).getAccountNumber(), selectedAccount.getValue())){
                        index = i;
                    }
                }
                if (index != -1){
                    stock = accounts.get(index).getAccountStock();
                }
                if (stock >= 1500000000) {
                    loanType.getItems().clear();
                    loanType.getItems().addAll( "انتخاب کنید", "وام ازدواج", "وام دانشجویی", "وام مسکن", "وام قرض الحسنه");
                    loanType.setValue("انتخاب کنید");
                } else if (stock >= 500000000) {
                    loanType.getItems().clear();
                    loanType.getItems().addAll( "انتخاب کنید","وام ازدواج", "وام دانشجویی", "وام قرض الحسنه");
                    loanType.setValue("انتخاب کنید");
                } else if (stock >= 100000000) {
                    loanType.getItems().clear();
                    loanType.getItems().addAll( "انتخاب کنید", "وام دانشجویی", "وام قرض الحسنه");
                    loanType.setValue("انتخاب کنید");
                } else if (stock >= 25000000) {
                    loanType.getItems().clear();
                    loanType.getItems().addAll( "انتخاب کنید", "وام دانشجویی");
                    loanType.setValue("انتخاب کنید");
                }
            });
        }else {
            selectedAccount.getItems().clear();
            selectedAccount.setValue("شما حساب معتبر ندارید");
            loanType.getItems().clear();
        }
    }
    public void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onRegisterClicked(ActionEvent event) {
    }
}
