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

public class CustomersPayingBillsController {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField billEarmark , paymentEarmark , billAmount;
private final ArrayList <String> BankAccount=new ArrayList<String>();
    @FXML
    private ChoiceBox<String> SelectedAccount;
    @FXML
    private Label errorText;
    Connection connection;
    PreparedStatement statement;
    public void onBackclicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void initialize()throws SQLException{
        connection= DriverManager.getConnection("jdbc:sqlserver://LAPTOP-0KSSE4QN;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "Nasimi" , "pedb1234");
        statement=connection.prepareStatement("select *from BankAccounts where CustomerID = ? ");
        statement.setInt(1,HelloController.userID);
        ResultSet resultSet=statement.executeQuery();
        BankAccount.add("انتخاب کنید");
        while (resultSet.next()){
            BankAccount.add(resultSet.getNString("AccountNumber"));
        }
        SelectedAccount.getItems().addAll(BankAccount);
        SelectedAccount.setValue(BankAccount.get(0));
    }


    public void onPaymentClicked(ActionEvent event) throws SQLException {
        if (billEarmark.getText().isEmpty()||paymentEarmark.getText().isEmpty()||billAmount.getText().isEmpty()){
            errorText.setText("پر کردن تمامی فیلد ها الزامی است.");
        } else if (billEarmark.getText().length()!=8 || paymentEarmark.getText().length()!=8) {
            errorText.setText("شناسه قبض و شناسه پرداخت باید 8 رقمی باشند.");
        } else if (!billEarmark.getText().matches("\\d+") || !paymentEarmark.getText().matches("\\d+") || !billAmount.getText().matches("\\d+")){
            errorText.setText("شناسه قبض و شناسه پرداخت و مبلغ قبض فقط باید شامل عدد باشد.");
        } else if (!billEarmark.getText().matches("1403\\d{4}")) {
            errorText.setText("فرمت شناسه قبض اشتباه است.");
        } else if (!paymentEarmark.getText().matches("1234\\d{4}")) {
            errorText.setText("فرمت شناسه پرداخت اشتباه است");
        } else  {
            statement = connection.prepareStatement("select AccountStock from BankAccounts where AccountNumber=?");
            statement.setNString(1,SelectedAccount.getValue());
            ResultSet Stock=statement.executeQuery();
            long stock = 0;
            while (Stock.next()){
                 stock = Stock.getLong("AccountStock");
            }
            if (stock < Long.parseLong(billAmount.getText())){
                errorText.setText("موجودی شما کافی نمی باشد.");
            }
            else {
                errorText.setText(" ");
                stock -= Long.parseLong(billAmount.getText());
                statement = connection.prepareStatement("update BankAccounts set AccountStock=? where AccountNumber=?");
                statement.setLong(1,stock);
                statement.setNString(2,SelectedAccount.getValue());
                int helia=statement.executeUpdate();
                errorText.setText("پرداخت با موفقیت انجام شد.");
           }
        }
    }
}
