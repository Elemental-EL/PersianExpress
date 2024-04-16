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
import org.example.persianexpress.Objects.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class CustomersRequestsSituationController {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private VBox mainVBox;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private ArrayList<CreateAccReq> createAccReqs = new ArrayList<>();
    private ArrayList<DeleteAccReq> deleteAccReqs = new ArrayList<>();
    private ArrayList<CardReq> cardReqs = new ArrayList<>();
    private ArrayList<LoanReq> loanReqs= new ArrayList<>();
    private ArrayList<CheckBookReq> checkBookReqs = new ArrayList<>();
    private ArrayList<Receipt> receipts = new ArrayList<>();

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select *from CreateAccountREQ where CustomerID = ?");
        statement.setInt(1 , HelloController.userID);
        resultSet = statement.executeQuery();
        int num = 0;
        while (resultSet.next()){
            CreateAccReq createAccReq = new CreateAccReq(resultSet.getInt("RequestID") , HelloController.userID);
            createAccReq.setAccType(resultSet.getNString("AccountType"));
            createAccReq.setReqDate(resultSet.getDate("RequestDate"));
            createAccReqs.add(createAccReq);
            num++;
        }
        statement = connection.prepareStatement("select *from DeleteAccountREQ where CustomerID = ?");
        statement.setInt(1 , HelloController.userID);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            DeleteAccReq deleteAccReq = new DeleteAccReq(resultSet.getInt("RequestID") , HelloController.userID);
            deleteAccReq.setAccID(resultSet.getInt("AccountID"));
            deleteAccReq.setSubstituteAccNum(resultSet.getString("SubstituteAccount"));
            deleteAccReq.setReqDate(resultSet.getDate("RequestDate"));
            deleteAccReqs.add(deleteAccReq);
            num++;
        }
        statement = connection.prepareStatement("select *from BankCardREQ where CustomerID = ?");
        statement.setInt(1 , HelloController.userID);
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            CardReq cardReq = new CardReq(resultSet.getInt("RequestID") , HelloController.userID);
            cardReq.setAccID(resultSet.getInt("SelectedAccountID"));
            cardReq.setReqDate(resultSet.getDate("RequestDate"));
            cardReqs.add(cardReq);
            num++;
        }
        statement = connection.prepareStatement("select *from LoanREQ where CustomerID = ?");
        statement.setInt(1 , HelloController.userID);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            LoanReq loanReq = new LoanReq(resultSet.getInt("RequestID") , HelloController.userID);
            loanReq.setLoanType(resultSet.getNString("LoanType"));
            loanReq.setAccID(resultSet.getInt("SelectedAccountID"));
            loanReq.setReqDate(resultSet.getDate("RequestDate"));
            loanReqs.add(loanReq);
            num++;
        }
        statement = connection.prepareStatement("select *from CheckBookREQ where CustomerID = ?");
        statement.setInt(1 , HelloController.userID);
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            CheckBookReq checkBookReq = new CheckBookReq(resultSet.getInt("RequestID") , HelloController.userID);
            checkBookReq.setCheckNum(resultSet.getInt("CheckNum"));
            checkBookReq.setAccID(resultSet.getInt("AccountID"));
            checkBookReq.setReqDate(resultSet.getDate("RequestDate"));
            checkBookReqs.add(checkBookReq);
            num++;
        }
        statement = connection.prepareStatement("select *from ReceiptChequeREQ where CustomerID = ?");
        statement.setInt(1 , HelloController.userID);
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            Receipt receipt = new Receipt(resultSet.getInt("RequestID") , HelloController.userID);
            receipt.setAccID(resultSet.getInt("AccountID"));
            receipt.setChequeSerial(resultSet.getLong("ChequeSerialNum"));
            receipt.setChequeAmount(resultSet.getLong("ChequeAmounts"));
            receipt.setReqDate(resultSet.getDate("RequestDate"));
            receipts.add(receipt);
            num++;
        }
        if (num > 2){
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 200*(num - 2));
            mainAnchorPane.setPrefHeight(mainAnchorPane.getPrefHeight() + 200*(num - 2));
        }
        ShowCreateAcc(createAccReqs);
        ShowDeleteAcc(deleteAccReqs);
        ShowCard(cardReqs);
        ShowLoan(loanReqs);
        ShowCheckBook(checkBookReqs);
        ShowReceipt(receipts);

    }

    private void ShowCreateAcc(ArrayList<CreateAccReq> createAccReqs){
        if (!createAccReqs.isEmpty()){
            for (CreateAccReq createAcc:createAccReqs) {
                Label typelbl = new Label("نوع درخواست :");
                Label typetxt = new Label("ایجاد حساب جدید");
                Label accTypelbl = new Label("نوع حساب درخواستی :");
                Label accTypetxt = new Label(createAcc.getAccType());
                Label reqDatelbl = new Label("تاریخ ثبت درخواست :");
                Label reqDatetxt = new Label(String.valueOf(createAcc.getReqDate()));
                Label statuslbl = new Label("وضعیت درخواست :");
                Label statustxt = new Label("در حال بررسی");
                typelbl.getStyleClass().add("labels");
                typetxt.getStyleClass().add("labels");
                accTypelbl.getStyleClass().add("labels");
                accTypetxt.getStyleClass().add("labels");
                reqDatelbl.getStyleClass().add("labels");
                reqDatetxt.getStyleClass().add("labels");
                statuslbl.getStyleClass().add("labels");
                statustxt.getStyleClass().add("labels");
                VBox vBox1 = new VBox(4);
                VBox vBox2 = new VBox(4);
                vBox1.setAlignment(Pos.CENTER_RIGHT);
                vBox1.getChildren().addAll(typelbl , accTypelbl , reqDatelbl , statuslbl);
                vBox2.getChildren().addAll(typetxt , accTypetxt , reqDatetxt, statustxt);
                BorderPane borderPane = new BorderPane();
                borderPane.setRight(vBox1);
                borderPane.setLeft(vBox2);
                BorderPane spacePane = new BorderPane();
                spacePane.setStyle("-fx-pref-height: 10; -fx-background-color: #fff");
                borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
                mainVBox.getChildren().addAll(borderPane , spacePane);
            }
        }
    }
    private void ShowDeleteAcc(ArrayList<DeleteAccReq> deleteAccReqs) throws SQLException {
        if (!deleteAccReqs.isEmpty()){
            for (DeleteAccReq deleteAcc: deleteAccReqs) {
                Label typelbl = new Label("نوع درخواست :");
                Label typetxt = new Label("بستن حساب");
                Label accNumlbl = new Label("شماره حساب اصلی :");
                Label accNumtxt = new Label();
                Label subAccNumlbl = new Label("شماره حساب جایگزین");
                Label subAccNumtxt = new Label(deleteAcc.getSubstituteAccNum());
                Label reqDatelbl = new Label("تاریخ ثبت درخواست :");
                Label reqDatetxt = new Label(String.valueOf(deleteAcc.getReqDate()));
                Label statuslbl = new Label("وضعیت درخواست :");
                Label statustxt = new Label("در حال بررسی");
                statement = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ?");
                statement.setInt(1 , deleteAcc.getAccID());
                resultSet = statement.executeQuery();
                while (resultSet.next()){
                    accNumtxt.setText(resultSet.getNString("AccountNumber"));
                }
                typelbl.getStyleClass().add("labels");
                typetxt.getStyleClass().add("labels");
                accNumlbl.getStyleClass().add("labels");
                accNumtxt.getStyleClass().add("labels");
                subAccNumlbl.getStyleClass().add("labels");
                subAccNumtxt.getStyleClass().add("labels");
                reqDatelbl.getStyleClass().add("labels");
                reqDatetxt.getStyleClass().add("labels");
                statuslbl.getStyleClass().add("labels");
                statustxt.getStyleClass().add("labels");
                VBox vBox1 = new VBox(5);
                vBox1.setAlignment(Pos.CENTER_RIGHT);
                VBox vBox2 = new VBox(5);
                vBox1.getChildren().addAll(typelbl , accNumlbl , subAccNumlbl , reqDatelbl , statuslbl);
                vBox2.getChildren().addAll(typetxt , accNumtxt , subAccNumtxt , reqDatetxt , statustxt);
                BorderPane borderPane = new BorderPane();
                borderPane.setRight(vBox1);
                borderPane.setLeft(vBox2);
                BorderPane spacePane = new BorderPane();
                spacePane.setStyle("-fx-pref-height: 10; -fx-background-color: #fff");
                borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
                mainVBox.getChildren().addAll(borderPane , spacePane);
            }
        }
    }
    private void ShowCard(ArrayList<CardReq> cardReqs) throws SQLException {
        if (!cardReqs.isEmpty()){
            for (CardReq cardReq: cardReqs) {
                Label typelbl = new Label("نوع درخواست :");
                Label typetxt = new Label("کارت بانکی");
                Label accNumlbl = new Label("شماره حساب مبدا :");
                Label accNumtxt = new Label();
                Label reqDatelbl = new Label("تاریخ ثبت درخواست :");
                Label reqDatetxt = new Label(String.valueOf(cardReq.getReqDate()));
                Label statuslbl = new Label("وضعیت درخواست :");
                Label statustxt = new Label("در حال بررسی");
                statement = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ?");
                statement.setInt(1 , cardReq.getAccID());
                resultSet = statement.executeQuery();
                while (resultSet.next()){
                    accNumtxt.setText(resultSet.getNString("AccountNumber"));
                }
                typelbl.getStyleClass().add("labels");
                typetxt.getStyleClass().add("labels");
                accNumlbl.getStyleClass().add("labels");
                accNumtxt.getStyleClass().add("labels");
                reqDatelbl.getStyleClass().add("labels");
                reqDatetxt.getStyleClass().add("labels");
                statuslbl.getStyleClass().add("labels");
                statustxt.getStyleClass().add("labels");
                VBox vBox1 = new VBox(4);
                VBox vBox2 = new VBox(4);
                vBox1.setAlignment(Pos.CENTER_RIGHT);
                vBox1.getChildren().addAll(typelbl , accNumlbl , reqDatelbl , statuslbl);
                vBox2.getChildren().addAll(typetxt , accNumtxt , reqDatetxt , statustxt);
                BorderPane borderPane = new BorderPane();
                borderPane.setRight(vBox1);
                borderPane.setLeft(vBox2);
                BorderPane spacePane = new BorderPane();
                spacePane.setStyle("-fx-pref-height: 10; -fx-background-color: #fff");
                borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
                mainVBox.getChildren().addAll(borderPane , spacePane);
            }
        }
    }
    private void ShowLoan(ArrayList<LoanReq> loanReqs) throws SQLException {
        if (!loanReqs.isEmpty()){
            for (LoanReq loanreq: loanReqs) {
                Label typelbl = new Label("نوع درخواست :");
                Label typetxt = new Label("وام");
                Label loanTypelbl = new Label("نوع وام :");
                Label loanTypetxt = new Label(loanreq.getLoanType());
                Label accNumlbl = new Label("حساب مبدا :");
                Label accNumtxt = new Label();
                Label reqDatelbl = new Label("تاریخ ثبت درخواست :");
                Label reqDatetxt = new Label(String.valueOf(loanreq.getReqDate()));
                Label statuslbl = new Label("وضعیت درخواست :");
                Label statustxt = new Label("در حال بررسی");
                statement = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ?");
                statement.setInt(1 , loanreq.getAccID());
                resultSet = statement.executeQuery();
                while (resultSet.next()){
                    accNumtxt.setText(resultSet.getNString("AccountNumber"));
                }
                typelbl.getStyleClass().add("labels");
                typetxt.getStyleClass().add("labels");
                loanTypelbl.getStyleClass().add("labels");
                loanTypetxt.getStyleClass().add("labels");
                accNumlbl.getStyleClass().add("labels");
                accNumtxt.getStyleClass().add("labels");
                reqDatelbl.getStyleClass().add("labels");
                reqDatetxt.getStyleClass().add("labels");
                statuslbl.getStyleClass().add("labels");
                statustxt.getStyleClass().add("labels");
                VBox vBox1 = new VBox(5);
                vBox1.setAlignment(Pos.CENTER_RIGHT);
                VBox vBox2 = new VBox(5);
                vBox1.getChildren().addAll(typelbl , loanTypelbl , accNumlbl , reqDatelbl , statuslbl);
                vBox2.getChildren().addAll(typetxt , loanTypetxt , accNumtxt , reqDatetxt , statustxt);
                BorderPane borderPane = new BorderPane();
                borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
                borderPane.setRight(vBox1);
                borderPane.setLeft(vBox2);
                BorderPane spacePane = new BorderPane();
                spacePane.setStyle("-fx-pref-height: 10; -fx-background-color: #fff");
                mainVBox.getChildren().addAll(borderPane , spacePane);
            }
        }
    }
    private void ShowCheckBook(ArrayList<CheckBookReq> checkBookReqs) throws SQLException {
        if (!checkBookReqs.isEmpty()){
            for (CheckBookReq checkreq: checkBookReqs) {
                Label typelbl = new Label("نوع درخواست :");
                Label typetxt = new Label("دسته چک");
                Label accNumlbl = new Label("حساب مبدا :");
                Label accNumtxt = new Label();
                Label checkNumlbl = new Label("تعداد برگه چک");
                Label checkNumtxt = new Label(String.valueOf(checkreq.getCheckNum()));
                Label reqDatelbl = new Label("تاریخ ثبت درخواست :");
                Label reqDatetxt = new Label(String.valueOf(checkreq.getReqDate()));
                Label statuslbl = new Label("وضعیت درخواست :");
                Label statustxt = new Label("در حال بررسی");
                statement = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ?");
                statement.setInt(1 , checkreq.getAccID());
                resultSet = statement.executeQuery();
                while (resultSet.next()){
                    accNumtxt.setText(resultSet.getNString("AccountNumber"));
                }
                typelbl.getStyleClass().add("labels");
                typetxt.getStyleClass().add("labels");
                accNumlbl.getStyleClass().add("labels");
                accNumtxt.getStyleClass().add("labels");
                checkNumlbl.getStyleClass().add("labels");
                checkNumtxt.getStyleClass().add("labels");
                reqDatelbl.getStyleClass().add("labels");
                reqDatetxt.getStyleClass().add("labels");
                statuslbl.getStyleClass().add("labels");
                statustxt.getStyleClass().add("labels");
                VBox vBox1 = new VBox(5);
                vBox1.setAlignment(Pos.CENTER_RIGHT);
                VBox vBox2 = new VBox(5);
                vBox1.getChildren().addAll(typelbl , accNumlbl , checkNumlbl , reqDatelbl , statuslbl);
                vBox2.getChildren().addAll(typetxt , accNumtxt , checkNumtxt ,  reqDatetxt , statustxt);
                BorderPane borderPane = new BorderPane();
                borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
                borderPane.setRight(vBox1);
                borderPane.setLeft(vBox2);
                BorderPane spacePane = new BorderPane();
                spacePane.setStyle("-fx-pref-height: 10; -fx-background-color: #fff");
                mainVBox.getChildren().addAll(borderPane , spacePane);
            }
        }
    }
    private void ShowReceipt(ArrayList<Receipt> receipts) throws SQLException {
        if (!receipts.isEmpty()){
            for (Receipt receipt: receipts) {
                Label typelbl = new Label("نوع درخواست :");
                Label typetxt = new Label("وصول چک");
                Label accNumlbl = new Label("حساب مبدا :");
                Label accNumtxt = new Label();
                Label checkSeriallbl = new Label("شماره سریال چک :");
                Label checkSerialtxt = new Label(String.valueOf(receipt.getChequeSerial()));
                Label amountlbl = new Label("مبلغ چک :");
                Label amounttxt = new Label(String.valueOf(receipt.getChequeAmount()));
                Label reqDatelbl = new Label("تاریخ ثبت درخواست :");
                Label reqDatetxt = new Label(String.valueOf(receipt.getReqDate()));
                Label statuslbl = new Label("وضعیت درخواست :");
                Label statustxt = new Label("در حال بررسی");
                statement = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ?");
                statement.setInt(1 , receipt.getAccID());
                resultSet = statement.executeQuery();
                while (resultSet.next()){
                    accNumtxt.setText(resultSet.getNString("AccountNumber"));
                }
                typelbl.getStyleClass().add("labels");
                typetxt.getStyleClass().add("labels");
                accNumlbl.getStyleClass().add("labels");
                accNumtxt.getStyleClass().add("labels");
                checkSeriallbl.getStyleClass().add("labels");
                checkSerialtxt.getStyleClass().add("labels");
                amountlbl.getStyleClass().add("labels");
                amounttxt.getStyleClass().add("labels");
                reqDatelbl.getStyleClass().add("labels");
                reqDatetxt.getStyleClass().add("labels");
                statuslbl.getStyleClass().add("labels");
                statustxt.getStyleClass().add("labels");
                VBox vBox1 = new VBox(6);
                vBox1.setAlignment(Pos.CENTER_RIGHT);
                VBox vBox2 = new VBox(6);
                vBox1.getChildren().addAll(typelbl , accNumlbl , checkSeriallbl , amountlbl , reqDatelbl , statuslbl);
                vBox2.getChildren().addAll(typetxt , accNumtxt , checkSerialtxt , amounttxt , reqDatetxt , statustxt);
                BorderPane borderPane = new BorderPane();
                borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
                borderPane.setRight(vBox1);
                borderPane.setLeft(vBox2);
                BorderPane spacePane = new BorderPane();
                spacePane.setStyle("-fx-pref-height: 10; -fx-background-color: #fff");
                mainVBox.getChildren().addAll(borderPane , spacePane);
            }
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
