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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.GharzolH;
import org.example.persianexpress.Objects.LoanReq;
import org.example.persianexpress.Objects.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
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
    @FXML
    private Text errorText;
    private User user;
    private Connection connection;
    private PreparedStatement statement;
    private ArrayList<GharzolH> accounts = new ArrayList<>();

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select *from BankAccounts where CustomerID = ?");
        statement.setInt(1 , HelloController.userID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            if (resultSet.getLong("AccountStock") >= 25000000 && resultSet.getBoolean("AccountAccess")){
                GharzolH account = new GharzolH(resultSet.getInt("AccountID") , resultSet.getInt("CustomerID"));
                account.setAccNumber(resultSet.getNString("AccountNumber"));
                account.setAccBalance(resultSet.getLong("AccountStock"));
                accounts.add(account);
            }
        }
        selectedAccount.getItems().add("انتخاب کنید");
        selectedAccount.setValue("انتخاب کنید");
        if (accounts.size() != 0) {
            for (GharzolH account:accounts) {
                selectedAccount.getItems().add(account.getAccNumber());
            }
            selectedAccount.setOnAction(event -> {
                int index = -1;
                long stock = 0;
                for ( int i = 0 ; i < accounts.size() ; i++){
                    if (Objects.equals(accounts.get(i).getAccNumber(), selectedAccount.getValue())){
                        index = i;
                    }
                }
                if (index != -1){
                    stock = accounts.get(index).getAccBalance();
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
        loanType.setOnAction(event -> {
            if (Objects.equals(loanType.getValue(), "انتخاب کنید")){
                LoanAmount.setText("");
            } else if (Objects.equals(loanType.getValue(), "وام دانشجویی")) {
                LoanAmount.setText("50000000 ریال");
            } else if (Objects.equals(loanType.getValue(), "وام قرض الحسنه")) {
                LoanAmount.setText("200000000 ریال");
            }else if (Objects.equals(loanType.getValue(), "وام ازدواج")){
                LoanAmount.setText("1000000000 ریال");
            } else if (Objects.equals(loanType.getValue(), "وام مسکن")) {
                LoanAmount.setText("3000000000 ریال");
            }
        });
        user = User.createUserObj(connection,HelloController.userID);
        applicantFirstName.setText(user.getfName());
        applicantFamily.setText(user.getLastname());
        applicantNCode.setText(user.getNationalCode());
        applicantFatherName.setText(user.getFatherName());
        applicantBPlace.setText(user.getbPlace());
        applicantPhoneNumber.setText(user.getPhNumber());
        applicantBDate.setValue(LocalDate.parse(user.getbDate()));
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

    public void onRegisterClicked(ActionEvent event) throws SQLException, IOException {
        String accNum = selectedAccount.getSelectionModel().getSelectedItem();
        String lType = loanType.getSelectionModel().getSelectedItem();
        if (accNum.equals("انتخاب کنید") || lType.equals("انتخاب کنید") || suretyFirstName.getText().trim().isEmpty() || suretyFamily.getText().trim().isEmpty() || suretyNCode.getText().trim().isEmpty() || suretyFatherName.getText().trim().isEmpty() || suretyBPlace.getText().trim().isEmpty() || suretyPhoneNumber.getText().trim().isEmpty() || suretyBDate.getValue() == null){
            errorText.setText("*پر کردن تمامی فیلد ها الزامی است.");
        } else if (!suretyNCode.getText().matches("\\d+")){
            errorText.setText("*کد ملی باید تنها شامل عدد باشد.");
        } else if (!suretyPhoneNumber.getText().matches("\\d+")){
            errorText.setText("*شماره موبایل باید تنها شامل عدد باشد.");
        } else if (!suretyPhoneNumber.getText().matches("09\\d{9}")){
            errorText.setText("*فرمت شماره موبایل اشتباه است.");
        } else if (!suretyNCode.getText().matches("\\d{10}")){
            errorText.setText("*کد ملی باید ده رقمی باشد.");
        } else {
            int suretyID = User.getUserID(connection,suretyNCode.getText());
            User surety = User.createUserObj(connection,suretyID);
            if (suretyFirstName.getText().equals(surety.getfName())&&suretyFamily.getText().equals(surety.getLastname())&&suretyNCode.getText().equals(surety.getNationalCode())&&suretyFatherName.getText().equals(surety.getFatherName())&&suretyBPlace.getText().equals(surety.getbPlace())&&suretyPhoneNumber.getText().equals(surety.getPhNumber())&&suretyBDate.getValue().equals(LocalDate.parse(surety.getbDate()))){
                if (surety.getNationalCode().equals(user.getNationalCode())){
                    errorText.setText("*شما نمی توانید ضامن خود باشید!");
                } else {
                    LoanReq.submitLoanReq(accNum, surety, lType, connection, user, applicantTXT);
                    Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    stage.centerOnScreen();
                }
            } else {
                errorText.setText("*اطلاعات ضامن صحیح نمی باشد.");
            }
        }
    }
}
