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
import org.example.persianexpress.Objects.Cheque;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class CustomersManagementIssuedChequeController {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private VBox mainVBox;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private ArrayList<Cheque> cheques = new ArrayList<>();


    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select *from ChequeHistory where CustomerID = ?");
        statement.setInt(1 , HelloController.userID);
        resultSet = statement.executeQuery();
        int num = 0;
        while(resultSet.next()){
            Cheque cheque = new Cheque(resultSet.getInt("ChequeID") , resultSet.getInt("CustomerID") , resultSet.getInt("AccountID"));
            cheque.setRecipientID(resultSet.getInt("RecipientID"));
            cheque.setChequeSerialNum(resultSet.getLong("ChequeSerialNum"));
            cheque.setAmount(resultSet.getLong("ChequeAmount"));
            cheque.setChequeDate(resultSet.getDate("ChequeDate"));
            cheque.setStatus(resultSet.getString("ChequeStatus"));
            cheques.add(cheque);
            num++;
        }
        if (num > 2){
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 250 * (num - 2));
            mainAnchorPane.setPrefHeight(mainAnchorPane.getPrefHeight() + 250 * (num - 2));
        }
        ShowCheque(cheques);
    }

    public void ShowCheque(ArrayList<Cheque> cheques) throws SQLException {
        for (Cheque cheque:cheques) {
            Label accNumlbl = new Label("حساب مبداء :");
            Label accNumtxt = new Label();
            Label recipientlbl = new Label("در وجه :");
            Label recipienttxt = new Label();
            Label serialNumlbl = new Label("شماره سریال چک :");
            Label serialNumtxt = new Label(String.valueOf(cheque.getChequeSerialNum()));
            Label amountlbl = new Label("به مبلغ :");
            Label amounttxt = new Label(String.valueOf(cheque.getAmount()));
            Label datelbl = new Label("به تاریخ :");
            Label datetxt = new Label(String.valueOf(cheque.getChequeDate()));
            Label statuslbl = new Label("وضعیت :");
            Label statustxt = new Label(cheque.getStatus());
            statement = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ?");
            statement.setInt(1 , cheque.getAccID());
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                accNumtxt.setText(resultSet.getNString("AccountNumber"));
            }
            statement = connection.prepareStatement("Select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , cheque.getRecipientID());
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                recipienttxt.setText(resultSet.getNString("FirstName") + " " + resultSet.getNString("LastName"));
            }
            accNumlbl.getStyleClass().add("labels2");
            accNumtxt.getStyleClass().add("labels2");
            recipientlbl.getStyleClass().add("labels2");
            recipienttxt.getStyleClass().add("labels2");
            serialNumlbl.getStyleClass().add("labels2");
            serialNumtxt.getStyleClass().add("labels2");
            amountlbl.getStyleClass().add("labels2");
            amounttxt.getStyleClass().add("labels2");
            datelbl.getStyleClass().add("labels2");
            datetxt.getStyleClass().add("labels2");
            statuslbl.getStyleClass().add("labels2");
            statustxt.getStyleClass().add("labels2");
            VBox vBox1 = new VBox(6);
            VBox vBox2 = new VBox(6);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox1.getChildren().addAll(accNumlbl , recipientlbl , serialNumlbl , amountlbl , datelbl , statuslbl);
            vBox2.getChildren().addAll(accNumtxt , recipienttxt , serialNumtxt , amounttxt , datetxt , statustxt);
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
