package org.example.persianexpress;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.Loan;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class CustomersLoanHistoryController {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private VBox mainVBox;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private ArrayList<Loan> longs = new ArrayList<>();
    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select *from LoansHistory where CustomerID = ?");
        statement.setInt(1 , HelloController.userID);
        resultSet = statement.executeQuery();
        int num = 0;
        while (resultSet.next()){
            Loan loan = new Loan(resultSet.getInt("LoanID") , resultSet.getInt("CustomerID"));
            loan.setAccID(resultSet.getInt("AccountID"));
            loan.setLoanType(resultSet.getNString("LoanType"));
            loan.setLoanAmount(resultSet.getLong("LoanAmount"));
            loan.setLoanDate(resultSet.getDate("LoanDate"));
            loan.setLoanInstalments(resultSet.getInt("LoanInstalments"));
            loan.setRemainingAmount(resultSet.getLong("RemainingAmount"));
            longs.add(loan);
            num++;
        }
        if (num > 2){
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 250 * (num - 2));
            mainAnchorPane.setPrefHeight(mainAnchorPane.getPrefHeight() + 250 * (num - 2));
        }
        ShowLoans(longs);
    }

    public void ShowLoans(ArrayList<Loan> longs) throws SQLException {
        for (Loan loan:longs) {
            Label acclbl = new Label("حساب مبدا :");
            Label acctxt = new Label();
            Label typelbl = new Label("نوع وام :");
            Label typetxt = new Label(loan.getLoanType());
            Label amountlbl = new Label("مبلغ وام :");
            Label amounttxt = new Label(String.valueOf(loan.getLoanAmount()));
            Label datelbl = new Label("تاریخ دریافت وام :");
            Label datetxt = new Label(String.valueOf(loan.getLoanDate()));
            Label instalmentlbl = new Label("اقساط باقی مانده :");
            Label instalmenttxt = new Label();
            Label remaininglbl = new Label("مبلغ باقی مانده :");
            Label remainingtxt = new Label(String.valueOf(loan.getRemainingAmount()));
            statement = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ?");
            statement.setInt(1 , loan.getAccID());
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                acctxt.setText(resultSet.getNString("AccountNumber"));
            }
            if (loan.getLoanInstalments() == 0){
                instalmenttxt.setText("تسویه شده");
                instalmenttxt.setStyle("-fx-text-fill: #00ff00");
            } else{
                instalmenttxt.setText(String.valueOf(loan.getLoanInstalments()));
            }
            acclbl.getStyleClass().add("labels");
            acctxt.getStyleClass().add("labels");
            typelbl.getStyleClass().add("labels");
            typetxt.getStyleClass().add("labels");
            amountlbl.getStyleClass().add("labels");
            amounttxt.getStyleClass().add("labels");
            datelbl.getStyleClass().add("labels");
            datetxt.getStyleClass().add("labels");
            instalmentlbl.getStyleClass().add("labels");
            instalmenttxt.getStyleClass().add("labels");
            remaininglbl.getStyleClass().add("labels");
            remainingtxt.getStyleClass().add("labels");
            VBox vBox1 = new VBox(6);
            VBox vBox2 = new VBox(6);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox1.getChildren().addAll(acclbl , typelbl , amountlbl , datelbl , instalmentlbl , remaininglbl);
            vBox2.getChildren().addAll(acctxt , typetxt , amounttxt , datetxt , instalmenttxt , remainingtxt);
            BorderPane borderPane = new BorderPane();
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            borderPane.getStyleClass().addAll("CPBorderPane1" , "gray");
            BorderPane spacePane = new BorderPane();
            spacePane.setStyle("-fx-pref-height: 10; -fx-background-color: #fff");
            mainVBox.getChildren().addAll(borderPane , spacePane);
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
}
