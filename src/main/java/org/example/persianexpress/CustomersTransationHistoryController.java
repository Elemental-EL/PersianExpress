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
import org.example.persianexpress.Objects.Transactions;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class CustomersTransationHistoryController {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private VBox mainVBox;
    private Connection connection;
    private PreparedStatement statement;
    private ArrayList<Transactions> transactions = new ArrayList<>();
    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select *from Transactions  where CustomerID = ? order by TransactionDate DESC");
        statement.setInt(1 , HelloController.userID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            Transactions transaction = new Transactions(resultSet.getInt("TransactionID"));
            transaction.setCustomerID(resultSet.getInt("CustomerID"));
            transaction.setTransactionType(resultSet.getNString("TransactionType"));
            transaction.setSenderAccNum(resultSet.getNString("SenderAccountNum"));
            transaction.setRecipientAccNum(resultSet.getNString("RecipientAccountNum"));
            transaction.setTransAmount(resultSet.getLong("TransactionAmount"));
            transaction.setTransDate(resultSet.getDate("TransactionDate"));
            transactions.add(transaction);
        }
        ShowTransactions(transactions);
        int num = transactions.size();
        if (num > 2){
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 200*(num - 2));
            mainAnchorPane.setPrefHeight(mainAnchorPane.getPrefHeight() + 200*(num -2  ));
        }
    }
    public void ShowTransactions(ArrayList<Transactions> transactions){
        for (Transactions transaction:transactions) {
            Label typelbl = new Label("نوع تراکنش :");
            Label typetxt = new Label();
            Label senderlbl = new Label("حساب مبدا :");
            Label sendertxt = new Label(transaction.getSenderAccNum());
            Label recipitlbl = new Label("حساب مقصد :");
            Label recipittxt = new Label(transaction.getRecipientAccNum());
            Label amountlbl = new Label("مبلغ :");
            Label amounttxt = new Label(String.valueOf(transaction.getTransAmount()));
            Label datelbl = new Label("تاریخ تراکنش :");
            Label datetxt = new Label(String.valueOf(transaction.getTransDate()));
            typelbl.getStyleClass().add("labels");
            if (Objects.equals(transaction.getTransactionType(), "دریافت")){
                typetxt.setText("واریز");
                typetxt.getStyleClass().add("labels");
                typetxt.setStyle("-fx-text-fill: #00ff00");
            }else {
                typetxt.setText("برداشت");
                typetxt.getStyleClass().add("labels");
                typetxt.setStyle("-fx-text-fill: #ff0000");
            }
            senderlbl.getStyleClass().add("labels");
            sendertxt.getStyleClass().add("labels");
            recipitlbl.getStyleClass().add("labels");
            recipittxt.getStyleClass().add("labels");
            amountlbl.getStyleClass().add("labels");
            amounttxt.getStyleClass().add("labels");
            datelbl.getStyleClass().add("labels");
            datetxt.getStyleClass().add("labels");
            VBox vBox1 = new VBox(5);
            VBox vBox2 = new VBox(5);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox1.getChildren().addAll(typelbl , senderlbl , recipitlbl , amountlbl , datelbl);
            vBox2.getChildren().addAll(typetxt , sendertxt , recipittxt , amounttxt , datetxt);
            BorderPane borderPane = new BorderPane();
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            BorderPane spacePane = new BorderPane();
            spacePane.setPrefHeight(10);
            spacePane.setStyle("-fx-background-color: #fff");
            borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
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
