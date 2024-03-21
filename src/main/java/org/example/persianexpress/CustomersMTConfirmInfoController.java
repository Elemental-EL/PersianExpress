package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.GharzolH;
import org.example.persianexpress.Objects.Sepordeh;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class CustomersMTConfirmInfoController {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField originAccNumTxt,originAccHolderTxt, destAccNumTxt,destAccHolderTxt, amountTxt,dateTxt,trackCode;
    private Connection connection;
    private LocalDate nowsDate = LocalDate.now();
    public static int TID=0;

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-IQ6LNQ5;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "PEDB" , "pedb1234");
        originAccNumTxt.setText(CustomersMoneyTransferController.orgAcc.getAccNumber());
        originAccHolderTxt.setText(CustomersMoneyTransferController.orgAcc.getHolderName(connection));
        destAccNumTxt.setText(CustomersMoneyTransferController.destAcc.getAccNumber());
        destAccHolderTxt.setText(CustomersMoneyTransferController.destAcc.getHolderName(connection));
        amountTxt.setText(String.valueOf(CustomersMoneyTransferController.amount)+" ریال");
    }
    public void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersMoneyTransfer.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onConfirmClicked(ActionEvent event) throws SQLException, IOException {
        CustomersMoneyTransferController.orgAcc.setAccBalance(CustomersMoneyTransferController.orgAcc.getAccBalance() - CustomersMoneyTransferController.amount);
        CustomersMoneyTransferController.destAcc.setAccBalance(CustomersMoneyTransferController.destAcc.getAccBalance() + CustomersMoneyTransferController.amount);
        CustomersMoneyTransferController.orgAcc.updateBalance(connection);
        CustomersMoneyTransferController.destAcc.updateBalance(connection);
        TID = GharzolH.submitTransaction(CustomersMoneyTransferController.orgAcc, "برداشت", connection, nowsDate);
        GharzolH.submitTransaction(CustomersMoneyTransferController.destAcc, "دریافت", connection, nowsDate);
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/ReceiptsView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }


}
