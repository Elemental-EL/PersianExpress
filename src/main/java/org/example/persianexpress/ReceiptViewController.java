package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReceiptViewController {
    private Stage stage;
    private Scene scene;
    @FXML
    private ImageView backBtn;
    @FXML
    private Label originAccNumTxt,originAccHolderTxt, destAccNumTxt,destAccHolderTxt, amountTxt,dateTxt,trackCode;
    @FXML
    private Button returnToMP;
    private Connection connection;
    private LocalDate nowsDate = LocalDate.now();

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-IQ6LNQ5;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "PEDB" , "pedb1234");
        originAccNumTxt.setText(CustomersMoneyTransferController.orgAcc.getAccNumber());
        originAccHolderTxt.setText(CustomersMoneyTransferController.orgAcc.getHolderName(connection));
        destAccNumTxt.setText(CustomersMoneyTransferController.destAcc.getAccNumber());
        destAccHolderTxt.setText(CustomersMoneyTransferController.destAcc.getHolderName(connection));
        amountTxt.setText(String.valueOf(CustomersMoneyTransferController.amount)+" ریال");
        dateTxt.setText(String.valueOf(nowsDate));
        trackCode.setText(String.valueOf(CustomersMTConfirmInfoController.TID));
    }
    @FXML
    void onLogoClicked(MouseEvent event){
    }
    @FXML
    void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
    public void onReturnToMPClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
}