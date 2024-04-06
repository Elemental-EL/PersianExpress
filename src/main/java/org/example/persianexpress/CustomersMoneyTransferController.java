package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.Card;
import org.example.persianexpress.Objects.GharzolH;
import org.example.persianexpress.Objects.Request;
import org.example.persianexpress.Objects.Sepordeh;

import java.io.IOException;
import java.sql.*;

public class CustomersMoneyTransferController {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField originAccNumTxt , destAccNumTxt , amountTxt;
    @FXML
    private Text errorText;
    private Connection connection;
    private ResultSet resultSet1;
    private ResultSet resultSet2;
    private boolean confirmed = false;
    public static Long amount;
    public static Sepordeh orgAcc,destAcc;

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-0KSSE4QN;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "Nasimi" , "138374");
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

    public void onConfirmClicked(ActionEvent event) throws IOException, SQLException {
        resultSet1 = GharzolH.getAvailableAccsForTransaction(connection);
        resultSet2 = GharzolH.getAvailableDestsForTransaction(connection);
        amount = Long.valueOf(amountTxt.getText());
        String orgAccNum = originAccNumTxt.getText();
        String destAccNum = destAccNumTxt.getText();
        if (originAccNumTxt.getText().matches("[0-9]{16}") && destAccNumTxt.getText().matches("[0-9]{16}")){
            orgAccNum = Card.getAccNumOfCard(connection,originAccNumTxt.getText());
            destAccNum = Card.getAccNumOfCard(connection,destAccNumTxt.getText());
        } else if (originAccNumTxt.getText().matches("[0-9]{16}")) {
            orgAccNum = Card.getAccNumOfCard(connection,originAccNumTxt.getText());
        } else if (destAccNumTxt.getText().matches("[0-9]{16}")){
            destAccNum = Card.getAccNumOfCard(connection,destAccNumTxt.getText());
        }
        orgAcc = Sepordeh.getAccForTransaction(orgAccNum , resultSet1);
        destAcc = Sepordeh.getAccForTransaction(destAccNum , resultSet2);
        confirmed = Sepordeh.transactionIsValid(orgAcc, destAcc, amount, errorText);
        if (confirmed) {
            Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersMTConfirmInfo.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();
        }
    }
}
