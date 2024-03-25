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
import org.example.persianexpress.Objects.Card;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class CustomersActiveCardsController {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane MainAnchorPane;
    @FXML
    private VBox MainVBox;
    private Connection connection;
    private PreparedStatement statement;
    private String accNum;
    private String accType;
    private long accStock;
    public void initialize() throws SQLException {
        ArrayList<Card> cards = new ArrayList<Card>();
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select *from BankCards where CustomerID = ?");
        statement.setInt(1 , HelloController.userID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            if (resultSet.getBoolean("CardAccess")){
                Card card = new Card(resultSet.getInt("CardID") , resultSet.getInt("CustomerID") , resultSet.getInt("AccountID"));
                card.setCardNumber(resultSet.getNString("CardNumber"));
                card.setCvv2(resultSet.getNString("CVV2"));
                card.setCardExpDate(resultSet.getDate("CardTerm"));
                cards.add(card);
            }
        }
        ShowActiveCards(cards);
    }
    private void ShowActiveCards(ArrayList<Card> cards) throws SQLException {
        int i = cards.size();
        MainAnchorPane.setPrefHeight(460);
        MainVBox.setPrefHeight(460);
        for (Card card:cards) {
            statement = connection.prepareStatement("select AccountNumber , AccountType , AccountStock from BankAccounts where CustomerID = ?");
            statement.setInt(1 , HelloController.userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                accNum = resultSet.getNString("AccountNumber");
                accType = resultSet.getNString("AccountType");
                accStock = resultSet.getLong("AccountStock");
            }
            Label accTypelbl = new Label("نوع حساب میدا :");
            Label accTypetxt = new Label(accType);
            Label accNumlbl = new Label("شماره حساب مبدا :");
            Label accNumtxt = new Label(accNum);
            Label cardNumlbl = new Label("شماره کارت :");
            Label cardNumtxt = new Label(card.getCardNumber());
            Label cvv2lbl = new Label(": CVV2");
            Label cvv2txt = new Label(card.getCvv2());
            Label cardTermlbl = new Label("تاریخ انقضای کارت :");
            Label cardTermtxt = new Label(String.valueOf(card.getCardExpDate()));
            Label accStocklbl = new Label("موجودی :");
            Label accStocktxt = new Label(String.valueOf(accStock));
            accTypelbl.getStyleClass().add("labels");
            accTypetxt.getStyleClass().add("labels");
            accNumlbl.getStyleClass().add("labels");
            accNumtxt.getStyleClass().add("labels");
            cardNumlbl.getStyleClass().add("labels");
            cardNumtxt.getStyleClass().add("labels");
            cvv2lbl.getStyleClass().add("labels");
            cvv2txt.getStyleClass().add("labels");
            cardTermlbl.getStyleClass().add("labels");
            cardTermtxt.getStyleClass().add("labels");
            accStocklbl.getStyleClass().add("labels");
            accStocktxt.getStyleClass().add("labels");
            VBox vBox1 = new VBox(6);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            VBox vBox2 = new VBox(6);
            vBox1.getChildren().addAll(accTypelbl , accNumlbl , cardNumlbl , cvv2lbl , cardTermlbl , accStocklbl);
            vBox2.getChildren().addAll(accTypetxt , accNumtxt , cardNumtxt , cvv2txt , cardTermtxt , accStocktxt);
            BorderPane borderPane = new BorderPane();
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
            BorderPane spacePane = new BorderPane();
            spacePane.setStyle("-fx-pref-height: 20");
            MainVBox.getChildren().addAll(borderPane , spacePane);
        }
    }
    public void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersProfile.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
}
