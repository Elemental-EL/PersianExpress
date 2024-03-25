package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class CustomersChargingServiceController {
    private Stage stage;
    private Scene scene;
    @FXML
    private Label errorText;
    @FXML
    private ChoiceBox<String> selectedAccount , Operator;
    private  ArrayList <String> BankAccount=new ArrayList<String>();
    private Connection connection;
    private PreparedStatement statement;
    @FXML
    private TextField phoneNumber , chargeAmount;
     private long stock = 0;
    public void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void initialize() throws SQLException {
        String[] operators = new String[]{"انتخاب کنید", "همراه اول" , "ایرانسل" , "رایتل"};
        Operator.getItems().addAll(operators);
        Operator.setValue(operators[0]);
        connection= DriverManager.getConnection("jdbc:sqlserver://LAPTOP-0KSSE4QN;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "Nasimi" , "pedb1234");
        statement=connection.prepareStatement("select  AccountNumber from BankAccounts where CustomerID=?  AND (AccountType = N'قرض الحسنه جاری' or AccountType = N'سپرده کوتاه مدت')");
        statement.setInt(1,HelloController.userID);
        ResultSet resultSet=statement.executeQuery();
        BankAccount.add("انتخاب کنید");
        while (resultSet.next()){
            BankAccount.add(resultSet.getNString("AccountNumber"));
        }
        selectedAccount.getItems().addAll(BankAccount);
        selectedAccount.setValue(BankAccount.get(0));
    }


    public void onBuyChargeClicked(ActionEvent event) throws SQLException {
        if (phoneNumber.getText().isEmpty()||chargeAmount.getText().isEmpty()){
            errorText.setText("پر کردن تمامی فیلد ها الزامی است.");
        } else if (!phoneNumber.getText().matches("09\\d{9}")) {
            errorText.setText("فرمت شماره تلفن اشتباه است.");
        } else if (!phoneNumber.getText().matches("\\d+")||!chargeAmount.getText().matches("\\d+")) {
            errorText.setText("شماره تلفن و مبلغ شارژ فقط باید شامل عدد باشد.");
        } else if (Objects.equals(Operator.getValue(), "همراه اول") && !phoneNumber.getText().matches("0919\\d{7}")) {
            errorText.setText("اپراتور انتخابی صحیح نمیباشد!");
        } else if (Objects.equals(Operator.getValue(), "ایرانسل") && !phoneNumber.getText().matches("0912\\d{7}")) {
            errorText.setText("اپراتور انتخابی صحیح نمیباشد!");
        } else if (Objects.equals(Operator.getValue(), "رایتل") && !phoneNumber.getText().matches("0921\\d{7}")) {
            errorText.setText("اپراتور انتخابی صحیح نمیباشد!");
        } else {
            statement = connection.prepareStatement("select AccountStock from BankAccounts where AccountNumber = ?");
            statement.setNString(1 , selectedAccount.getValue());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                stock = resultSet.getLong("AccountStock");
            }
            if (stock < Long.parseLong(chargeAmount.getText())){
                System.out.println("موجودی شما کافی نمی باشد.");
            }
            else {
                stock -=Long.parseLong(chargeAmount.getText());
                statement=connection.prepareStatement("update BankAccounts set AccountStock =? where AccountNumber=?");
                statement.setLong(1,stock);
                statement.setNString(2,selectedAccount.getValue());
                int resultSet1=statement.executeUpdate();
                errorText.setText("خرید با موفقیت انجام شد.");
            }
        }
    }
}
