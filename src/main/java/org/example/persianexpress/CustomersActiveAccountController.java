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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.GharzolH;
import org.example.persianexpress.Objects.Sepordeh;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class CustomersActiveAccountController {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane showAcountsPane ;
    @FXML
    private VBox showAcountsVBox ;
    private Connection connection;
    private PreparedStatement statement;
    public void initialize(){
        ArrayList<GharzolH> gharzolHSAcc = new ArrayList<>();
        ArrayList<Sepordeh> sepordehAcc = new ArrayList<>();
        int i = 0;
        try {
            connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
            PreparedStatement statement = connection.prepareStatement("select *from BankAccounts");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                if (resultSet.getInt("CustomerID") == HelloController.userID){
                    String accType = resultSet.getNString("AccountType");
                    if (Objects.equals(accType, "قرض الحسنه جاری") || Objects.equals(accType, "قرض الحسنه سپرده")){
                        if (resultSet.getBoolean("AccountAccess")){
                            GharzolH account1 = new GharzolH();
                            account1.setAccNumber(resultSet.getNString("AccountNumber"));
                            account1.setAccType(resultSet.getNString("AccountType"));
                            account1.setAccBalance(resultSet.getLong("AccountStock"));
                            account1.setAccAccess(resultSet.getBoolean("AccountAccess"));
                            gharzolHSAcc.add(account1);
                            i++;
                        }
                    } else if (Objects.equals(accType, "سپرده کوتاه مدت") || Objects.equals(accType, "سپرده بلند مدت")) {
                        if (resultSet.getBoolean("AccountAccess")){
                            Sepordeh account2 = new Sepordeh(resultSet.getInt("AccountID"),resultSet.getInt("CustomerID"));
                            account2.setAccNumber(resultSet.getNString("AccountNumber"));
                            account2.setAccType(resultSet.getNString("AccountType"));
                            account2.setAccBalance(resultSet.getLong("AccountStock"));
                            account2.setAccInterest(resultSet.getInt("AccountProfit"));
                            account2.setAccTerminationDate(resultSet.getDate("AccountTerm"));
                            account2.setAccAccess(resultSet.getBoolean("AccountAccess"));
                            sepordehAcc.add(account2);
                            i++;
                        }
                    }
                }
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error");
        }
        ShowAllAccounts(gharzolHSAcc , sepordehAcc , i);
    }
    private void ShowAllAccounts(ArrayList<GharzolH> gharzolHS , ArrayList<Sepordeh> sepordehs  , int i){
        for (GharzolH gharz:gharzolHS) {
            Label accTypelbl = new Label("نوع حساب : ");
            Label accNumlbl = new Label("شماره حساب : ");
            Label accStocklbl = new Label("موجودی :(ریال) ");
            Label accTypetxt = new Label(gharz.getAccType());
            Label accNumtxt = new Label(gharz.getAccNumber());
            Label accStocktxt = new Label(String.valueOf(gharz.getAccBalance()));
            Label accAccesslbl = new Label("وضعیت حساب :");
            Label accAccesstxt = new Label("");
            if (gharz.isAccAccess()){
                accAccesstxt.setText("فعال");
            } else if (!gharz.isAccAccess()) {
                accAccesstxt.setText("مسدود");
            }
            accTypelbl.getStyleClass().add("labels");
            accNumlbl.getStyleClass().add("labels");
            accStocklbl.getStyleClass().add("labels");
            accTypetxt.getStyleClass().add("labels");
            accNumtxt.getStyleClass().add("labels");
            accStocktxt.getStyleClass().add("labels");
            accAccesslbl.getStyleClass().add("labels");
            accAccesstxt.getStyleClass().add("labels");
            VBox vBox1 = new VBox(4);
            VBox vBox2 = new VBox(4);
            vBox1.getChildren().addAll(accTypelbl , accNumlbl , accStocklbl , accAccesslbl);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox2.getChildren().addAll(accTypetxt , accNumtxt , accStocktxt , accAccesstxt);
            BorderPane borderPane = new BorderPane();
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
            BorderPane spacePane = new BorderPane();
            spacePane.setStyle("-fx-pref-height: 20");
            showAcountsVBox.getChildren().addAll(borderPane , spacePane);
        }
        for (Sepordeh sepordeh: sepordehs) {
            Label accTypelbl = new Label("نوع حساب : ");
            Label accNumlbl = new Label("شماره حساب : ");
            Label accStocklbl = new Label("موجودی :(ریال) ");
            Label accInterestlbl = new Label("نرخ سود سالانه : ");
            Label accTermlbl = new Label("تاریخ سررسید سپرده : ");
            Label accTypetxt = new Label(sepordeh.getAccType());
            Label accNumtxt = new Label(sepordeh.getAccNumber());
            Label accStocktxt = new Label(String.valueOf(sepordeh.getAccBalance()));
            Label accInteresttxt = new Label(String.valueOf(sepordeh.getAccInterest()) + "%");
            Label accTermtxt = new Label(String.valueOf(sepordeh.getAccTerminationDate()));
            Label accAccesslbl = new Label("وضعیت حساب :");
            Label accAccesstxt = new Label("");
            if (sepordeh.isAccAccess()){
                accAccesstxt.setText("فعال");
            } else if (!sepordeh.isAccAccess()) {
                accAccesstxt.setText("مسدود");
            }
            accTypelbl.getStyleClass().add("labels");
            accNumlbl.getStyleClass().add("labels");
            accStocklbl.getStyleClass().add("labels");
            accInterestlbl.getStyleClass().add("labels");
            accTermlbl.getStyleClass().add("labels");
            accTypetxt.getStyleClass().add("labels");
            accNumtxt.getStyleClass().add("labels");
            accStocktxt.getStyleClass().add("labels");
            accInteresttxt.getStyleClass().add("labels");
            accTermtxt.getStyleClass().add("labels");
            accAccesslbl.getStyleClass().add("labels");
            accAccesstxt.getStyleClass().add("labels");
            VBox vBox1 = new VBox(6);
            VBox vBox2 = new VBox(6);
            vBox1.getChildren().addAll(accTypelbl , accNumlbl , accStocklbl , accInterestlbl , accTermlbl , accAccesslbl);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox2.getChildren().addAll(accTypetxt , accNumtxt , accStocktxt , accInteresttxt , accTermtxt , accAccesstxt);
            BorderPane borderPane = new BorderPane();
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
            BorderPane spacePane = new BorderPane();
            spacePane.setStyle("-fx-pref-height: 20");
            showAcountsVBox.getChildren().addAll(borderPane , spacePane);
        }
        showAcountsPane.setPrefHeight(470);
        showAcountsVBox.setPrefHeight(470);
        if (i >= 3){
            showAcountsPane.setPrefHeight(showAcountsPane.getPrefHeight() + 170*(i-2));
            showAcountsVBox.setPrefHeight(showAcountsVBox.getPrefHeight() + 170*(i-2));
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
