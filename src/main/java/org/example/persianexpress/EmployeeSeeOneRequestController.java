package org.example.persianexpress;

import javafx.event.ActionEvent;
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
import java.time.LocalDate;
import java.util.Objects;

public class EmployeeSeeOneRequestController {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private VBox mainVBox;
    private Connection connection;
    private ResultSet resultSet;
    private LocalDate nowsDate = LocalDate.now();
    private String reqType;

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-0KSSE4QN;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "Nasimi" , "138374");
        resultSet = Request.getReq(connection,EmployeeSeeRequestsController.REQID);
        reqType = Request.getReqType(EmployeeSeeRequestsController.REQID);
        mainPane.setPrefHeight(418);
        mainVBox.setPrefHeight(418);
        if (Objects.equals(Request.getReqType(EmployeeSeeRequestsController.REQID), "CreateAccountREQ")){
            ShowCreateAccREQ(resultSet);
        } else if (Objects.equals(Request.getReqType(EmployeeSeeRequestsController.REQID), "DeleteAccountREQ")) {
            ShowDeleteAccREQ(resultSet);
        } else if (Objects.equals(Request.getReqType(EmployeeSeeRequestsController.REQID), "BankCardREQ")) {
            ShowBankCardREQ(resultSet);
        } else if (Objects.equals(Request.getReqType(EmployeeSeeRequestsController.REQID), "LoanREQ")) {
            ShowLoanREQ(resultSet);
        } else if (Objects.equals(Request.getReqType(EmployeeSeeRequestsController.REQID), "CheckBookREQ")) {
            ShowChequeREQ(resultSet);
        } else if (Objects.equals(Request.getReqType(EmployeeSeeRequestsController.REQID), "ReceiptChequeREQ")) {
            ShowReceiptCheque(resultSet);
        }
    }

    public void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Persian Express");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onRejectClicked(ActionEvent event) throws SQLException, IOException {
        resultSet.beforeFirst();
        while (resultSet.next()) {
            Request.passToHistory(connection, resultSet, false);
            Request.deleteFromREQS(connection, resultSet,reqType);
        }
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Persian Express");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onConfirmClicked(ActionEvent event) throws SQLException, IOException {
        resultSet.beforeFirst();
        if (reqType.equals("CreateAccountREQ")){
            while (resultSet.next()) {
                if (resultSet.getInt("CustomerID") == 100) {
                    int uID = User.createUser(connection, resultSet);
                    GharzolH.createBankAcc(connection, uID, GharzolH.generateAccNum(connection, resultSet.getNString("AccountType")), resultSet.getNString("AccountType"), nowsDate);
                    Request.passToHistory(connection, resultSet,uID, true);
                    Request.deleteFromREQS(connection, resultSet,reqType);
                    Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setTitle("Persian Express");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    stage.centerOnScreen();
                } else {
                    GharzolH.createBankAcc(connection, resultSet.getInt("CustomerID"), GharzolH.generateAccNum(connection, resultSet.getNString("AccountType")), resultSet.getNString("AccountType"), nowsDate);
                    Request.passToHistory(connection, resultSet, true);
                    Request.deleteFromREQS(connection, resultSet, reqType);
                    Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setTitle("Persian Express");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    stage.centerOnScreen();
                }
            }
        } else if (reqType.equals("CheckBookREQ")) {
            while (resultSet.next()){
                CheckBookReq.createChequeStock(connection, resultSet);
                Request.passToHistory(connection, resultSet, true);
                Request.deleteFromREQS(connection,resultSet,reqType);
                Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Persian Express");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            }
        } else if (reqType.equals("BankCardREQ")) {
            while (resultSet.next()){
                CardReq.createCard(connection,resultSet);
                Request.passToHistory(connection,resultSet,true);
                Request.deleteFromREQS(connection,resultSet,reqType);
                Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Persian Express");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            }
        } else if (reqType.equals("DeleteAccountREQ")) {
            while (resultSet.next()){
                DeleteAccReq.deleteAccount(connection,resultSet);
                Request.passToHistory(connection,resultSet,true);
                Request.deleteFromREQS(connection,resultSet,reqType);
                Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Persian Express");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            }
        } else if (reqType.equals("ReceiptChequeREQ")) {
            while (resultSet.next()){
                long serial = resultSet.getLong("ChequeSerialNum");
                ResultSet res = StockCheque.getCheque(serial, connection);
                res.next();
                long accBalance = GharzolH.getAccountBalance(res.getInt("AccountID"),connection);
                long debt = res.getLong("ChequeAmount");
                boolean isValid = false;
                String status = "برگشت خورده";
                if (accBalance>=debt){
                    isValid = true;
                    status = "وصول شده";
                }
                if (isValid){
                    long newBalacne = accBalance - debt;
                    GharzolH.updateBalance(newBalacne, res, connection);
                    long destAccBalance = GharzolH.getAccountBalance(resultSet.getInt("AccountID"),connection);
                    destAccBalance += debt;
                    GharzolH.updateBalance(destAccBalance,resultSet,connection);
                    StockCheque.setNewStatusForCheque(status, res, connection);
                    Request.passToHistory(connection,resultSet,true);
                    Request.deleteFromREQS(connection,resultSet,reqType);
                    Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setTitle("Persian Express");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    stage.centerOnScreen();
                } else {
                    int accID = res.getInt("AccountID");
                    GharzolH.suspendAccount(accID, connection);
                    StockCheque.setNewStatusForCheque(status, res, connection);
                    Request.passToHistory(connection,resultSet,true);
                    Request.deleteFromREQS(connection,resultSet,reqType);
                    Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setTitle("Persian Express");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    stage.centerOnScreen();
                }
            }
        } else if (reqType.equals("LoanREQ")){
            while (resultSet.next()) {
                GharzolH acc = new GharzolH(resultSet.getInt("SelectedAccountID"), resultSet.getInt("CustomerID"));
                String loanType = resultSet.getNString("LoanType");
                long loanAmount = LoanReq.getLoanAmount(resultSet.getNString("LoanType"));
                long oldBalance = acc.getAccBalance();
                long newBalance = oldBalance + loanAmount;
                int instalments = LoanReq.getLoanInstalments(loanType);
                acc.setAccBalance(newBalance);
                acc.updateBalance(connection);
                LoanReq.createLoan(loanType, loanAmount, instalments, connection, resultSet);
                Request.passToHistory(connection, resultSet, true);
                Request.deleteFromREQS(connection, resultSet, reqType);
                Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Persian Express");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            }
        }
    }

    public void ShowCreateAccREQ(ResultSet resultSet) throws SQLException {
        while (resultSet.next()){
            Label fullNamelbl = new Label("نام و نام خانوادگی :");
            Label fullNameTxt = new Label(resultSet.getNString("FirstName") + " " + resultSet.getNString("LastName"));
            Label reqTypelbl = new Label("نوع درخواست :");
            Label reqTypeTxt = new Label("ایجاد حساب جدید");
            Label accTypelbl = new Label("نوع حساب :");
            Label accTypeTxt = new Label(resultSet.getNString("AccountType"));
            Label userNamelbl = new Label("نام کاربری :");
            Label userNameTxt = new Label(resultSet.getNString("CustomerUN"));
            Label nCodelbl = new Label("کد ملی :");
            Label nCodeTxt = new Label(resultSet.getString("NationalCode"));
            Label fatherNamelbl = new Label("نام پدر :");
            Label fatherNameTxt = new Label(resultSet.getNString("FatherName"));
            Label bDatelbl = new Label("تاریخ تولد :");
            Label bDateTxt = new Label(String.valueOf(resultSet.getDate("BirthDate")));
            Label bPlacelbl = new Label("محل تولد :");
            Label bPlaceTxt = new Label(resultSet.getNString("BirthPlace"));
            Label phNumberlbl = new Label("شماره تماس :");
            Label phNumberTxt = new Label(resultSet.getNString("PhoneNumber"));
            Label hPhNumberlbl = new Label("شماره منزل :");
            Label hPhNumberTxt = new Label(resultSet.getNString("HomePhoneNumber"));
            Label hAddresslbl = new Label("آدرس منزل :");
            Label hAddressTxt = new Label(resultSet.getNString("HomeAddress"));
            Label pCodelbl = new Label("گد پستی منزل :");
            Label pCodeTxt = new Label(resultSet.getNString("PostCode"));
            Label ReqDatelbl = new Label("تاریخ ثبت درخواست :");
            Label ReqDateTxt = new Label(String.valueOf(resultSet.getDate("RequestDate")));
            fullNamelbl.getStyleClass().add("labels");
            fullNameTxt.getStyleClass().add("labels");
            reqTypelbl.getStyleClass().add("labels");
            reqTypeTxt.getStyleClass().add("labels");
            accTypelbl.getStyleClass().add("labels");
            accTypeTxt.getStyleClass().add("labels");
            userNamelbl.getStyleClass().add("labels");
            userNameTxt.getStyleClass().add("labels");
            nCodelbl.getStyleClass().add("labels");
            nCodeTxt.getStyleClass().add("labels");
            fatherNamelbl.getStyleClass().add("labels");
            fatherNameTxt.getStyleClass().add("labels");
            bDatelbl.getStyleClass().add("labels");
            bDateTxt.getStyleClass().add("labels");
            bPlacelbl.getStyleClass().add("labels");
            bPlaceTxt.getStyleClass().add("labels");
            phNumberlbl.getStyleClass().add("labels");
            phNumberTxt.getStyleClass().add("labels");
            hPhNumberlbl.getStyleClass().add("labels");
            hPhNumberTxt.getStyleClass().add("labels");
            hAddresslbl.getStyleClass().add("labels");
            hAddressTxt.getStyleClass().add("labels");
            pCodelbl.getStyleClass().add("labels");
            pCodeTxt.getStyleClass().add("labels");
            ReqDatelbl.getStyleClass().add("labels");
            ReqDateTxt.getStyleClass().add("labels");
            VBox vBox1 = new VBox(13);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            VBox vBox2 = new VBox(13);
            vBox1.getChildren().addAll(fullNamelbl ,reqTypelbl , accTypelbl , userNamelbl , nCodelbl , fatherNamelbl , bDatelbl , bPlacelbl , phNumberlbl , hPhNumberlbl , hAddresslbl , pCodelbl , ReqDatelbl);
            vBox2.getChildren().addAll(fullNameTxt ,reqTypeTxt , accTypeTxt , userNameTxt , nCodeTxt , fatherNameTxt , bDateTxt , bPlaceTxt , phNumberTxt , hPhNumberTxt , hAddressTxt , pCodeTxt , ReqDateTxt);
            BorderPane borderPane = new BorderPane();
            borderPane.getStyleClass().add("CPBorderPane1");
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            mainVBox.getChildren().add(borderPane);
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 120);
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 120);
        }
    }
    public void ShowDeleteAccREQ(ResultSet resultSet) throws SQLException {
        mainPane.setPrefHeight(418);
        mainVBox.setPrefHeight(418);
        while (resultSet.next()){
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , resultSet.getInt("CustomerID"));
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next()){
                firstName = resultSet1.getNString("FirstName");
                lastName = resultSet1.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , resultSet.getInt("AccountID"));
            ResultSet resultSet2 = statement1.executeQuery();
            while (resultSet2.next()){
                accNum = resultSet2.getNString("AccountNumber");
            }
            Label fullNamelbl = new Label("نام و نام خانوادگی :");
            Label fullNameTxt = new Label(firstName + " " + lastName);
            Label reqTypelbl = new Label("نوع درخواست :");
            Label reqTypeTxt = new Label("حذف حساب بانکی");
            Label selectAcclbl = new Label("شماره حساب انتخاب شده :");
            Label selectAccTxt = new Label(accNum);
            Label substituteAcclbl = new Label("شماره حساب جایگزین :");
            Label substituteAccTxt = new Label(resultSet.getNString("SubstituteAccount"));
            Label reqDatelbl = new Label("تاریخ ثبت درخواست :");
            Label reqDateTxt = new Label(String.valueOf(resultSet.getDate("RequestDate")));
            fullNamelbl.getStyleClass().add("labels");
            fullNameTxt.getStyleClass().add("labels");
            reqTypelbl.getStyleClass().add("labels");
            reqTypeTxt.getStyleClass().add("labels");
            selectAcclbl.getStyleClass().add("labels");
            selectAccTxt.getStyleClass().add("labels");
            substituteAcclbl.getStyleClass().add("labels");
            substituteAccTxt.getStyleClass().add("labels");
            reqDatelbl.getStyleClass().add("labels");
            reqDateTxt.getStyleClass().add("labels");
            VBox vBox1 = new VBox(5);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            VBox vBox2 = new VBox(5);
            vBox1.getChildren().addAll(fullNamelbl , reqTypelbl , selectAcclbl , substituteAcclbl , reqDatelbl);
            vBox2.getChildren().addAll(fullNameTxt , reqTypeTxt , selectAccTxt , substituteAccTxt , reqDateTxt);
            BorderPane borderPane = new BorderPane();
            borderPane.getStyleClass().add("CPBorderPane1");
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            mainVBox.getChildren().add(borderPane);
        }
    }
    public void ShowBankCardREQ(ResultSet resultSet) throws SQLException {
        mainPane.setPrefHeight(418);
        mainVBox.setPrefHeight(418);
        while (resultSet.next()){
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , resultSet.getInt("CustomerID"));
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next()){
                firstName = resultSet1.getNString("FirstName");
                lastName = resultSet1.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , resultSet.getInt("SelectedAccountID"));
            ResultSet resultSet2 = statement1.executeQuery();
            while (resultSet2.next()){
                accNum = resultSet2.getNString("AccountNumber");
            }
            Label fullNamelbl = new Label("نام و نام خانوادگی :");
            Label fullNameTxt = new Label(firstName + " " + lastName);
            Label reqTypelbl = new Label("نوع درخواست :");
            Label reqTypeTxt = new Label("درخواست کارت بانکی");
            Label selectAcclbl = new Label("حساب انتخاب شده :");
            Label selectAccTxt = new Label(accNum);
            Label reqDatelbl = new Label("تاریخ ثبت درخواست :");
            Label reqDateTxt = new Label(String.valueOf(resultSet.getDate("RequestDate")));
            fullNamelbl.getStyleClass().add("labels");
            fullNameTxt.getStyleClass().add("labels");
            reqTypelbl.getStyleClass().add("labels");
            reqTypeTxt.getStyleClass().add("labels");
            selectAcclbl.getStyleClass().add("labels");
            selectAccTxt.getStyleClass().add("labels");
            reqDatelbl.getStyleClass().add("labels");
            reqDateTxt.getStyleClass().add("labels");
            VBox vBox1 = new VBox(4);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            VBox vBox2 = new VBox(4);
            vBox1.getChildren().addAll(fullNamelbl , reqTypelbl , selectAcclbl , reqDatelbl);
            vBox2.getChildren().addAll(fullNameTxt , reqTypeTxt , selectAccTxt , reqDateTxt);
            BorderPane borderPane = new BorderPane();
            borderPane.getStyleClass().add("CPBorderPane1");
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            mainVBox.getChildren().add(borderPane);
        }
    }
    public void ShowLoanREQ(ResultSet resultSet) throws SQLException {
        mainPane.setPrefHeight(418);
        mainVBox.setPrefHeight(418);
        while (resultSet.next()){
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , resultSet.getInt("CustomerID"));
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next()){
                firstName = resultSet1.getNString("FirstName");
                lastName = resultSet1.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , resultSet.getInt("SelectedAccountID"));
            ResultSet resultSet2 = statement1.executeQuery();
            while (resultSet2.next()){
                accNum = resultSet2.getNString("AccountNumber");
            }
            Label fullNamelbl = new Label("نام و نام خانوادگی");
            Label fullNameTxt = new Label(firstName + " " + lastName);
            Label reqTypelbl = new Label("نوع درخواست :");
            Label reqTypeTxt = new Label("درخواست وام");
            Label loanTypelbl = new Label("نوع وام درخواستی :");
            Label loanTypeTxt = new Label(resultSet.getNString("LoanType"));
            Label selectAcclbl = new Label("حساب انتخاب شده :");
            Label selectAccTxt = new Label(accNum);
            Label SfullNamelbl = new Label("نام و نام خانوادگی ضامن :");
            Label SfullNameTxt = new Label(resultSet.getNString("SuretyFirstName") + " " + resultSet.getNString("SuretyLastName"));
            Label SnCodelbl = new Label("کد ملی ضامن :");
            Label SnCodeTxt = new Label(resultSet.getString("SuretyNationalCode"));
            Label SfatherNamelbl = new Label("نام پدر ضامن :");
            Label SfatherNameTxt = new Label(resultSet.getNString("SuretyFatherName"));
            Label SbDatelbl = new Label("تاریخ تولد ضامن :");
            Label SbDateTxt = new Label(String.valueOf(resultSet.getDate("SuretyBirthDate")));
            Label SbPlacelbl = new Label("محل تولد ضامن :");
            Label SbPlaceTxt = new Label(resultSet.getNString("SuretyBirthPlace"));
            Label SphNumlbl = new Label("شماره تماس ضامن :");
            Label SphNumTxt = new Label(resultSet.getString("SuretyPhoneNumber"));
            Label reqDatelbl = new Label("تاریخ ثبت درخواست :");
            Label reqDateTxt = new Label(String.valueOf("RequestDate"));
            fullNamelbl.getStyleClass().add("labels");
            fullNameTxt.getStyleClass().add("labels");
            reqTypelbl.getStyleClass().add("labels");
            reqTypeTxt.getStyleClass().add("labels");
            loanTypelbl.getStyleClass().add("labels");
            loanTypeTxt.getStyleClass().add("labels");
            selectAcclbl.getStyleClass().add("labels");
            selectAccTxt.getStyleClass().add("labels");
            SfullNamelbl.getStyleClass().add("labels");
            SfullNameTxt.getStyleClass().add("labels");
            SnCodelbl.getStyleClass().add("labels");
            SnCodeTxt.getStyleClass().add("labels");
            SfatherNamelbl.getStyleClass().add("labels");
            SfatherNameTxt.getStyleClass().add("labels");
            SbDatelbl.getStyleClass().add("labels");
            SbDateTxt.getStyleClass().add("labels");
            SbPlacelbl.getStyleClass().add("labels");
            SbPlaceTxt.getStyleClass().add("labels");
            SphNumlbl.getStyleClass().add("labels");
            SphNumTxt.getStyleClass().add("labels");
            reqDatelbl.getStyleClass().add("labels");
            reqDateTxt.getStyleClass().add("labels");
            VBox vBox1 = new VBox(11);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            VBox vBox2 = new VBox(11);
            vBox1.getChildren().addAll(fullNamelbl , reqTypelbl , loanTypelbl , selectAcclbl , SfullNamelbl , SnCodelbl , SfatherNamelbl , SbDatelbl , SbPlacelbl , SphNumlbl , reqDatelbl);
            vBox2.getChildren().addAll(fullNameTxt , reqTypeTxt , loanTypeTxt , selectAccTxt , SfullNameTxt , SnCodeTxt , SfatherNameTxt , SbDateTxt , SbPlaceTxt , SphNumTxt , reqDateTxt);
            BorderPane borderPane = new BorderPane();
            borderPane.getStyleClass().add("CPBorderPane1");
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            mainVBox.getChildren().add(borderPane);
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 300);
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 300);
        }
    }
    public void ShowChequeREQ(ResultSet resultSet) throws SQLException {
        mainPane.setPrefHeight(418);
        mainVBox.setPrefHeight(418);
        while (resultSet.next()){
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , resultSet.getInt("CustomerID"));
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next()){
                firstName = resultSet1.getNString("FirstName");
                lastName = resultSet1.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , resultSet.getInt("AccountID"));
            ResultSet resultSet2 = statement1.executeQuery();
            while (resultSet2.next()){
                accNum = resultSet2.getNString("AccountNumber");
            }
            Label fullNamelbl = new Label("نام و نام خانوادگی");
            Label fullNameTxt = new Label(firstName + " " + lastName);
            Label reqTypelbl = new Label("نوع درخواست :");
            Label reqTypeTxt = new Label("دسته چک");
            Label selectAcclbl = new Label("حساب انتخاب شده :");
            Label selectAccTxt = new Label(accNum);
            Label chequeNumlbl = new Label("تعداد برگه های چک :");
            Label chequeNumTxt = new Label(String.valueOf(resultSet.getInt("CheckNum")));
            Label reqDatelbl = new Label("تاریخ ثبت درخواست :");
            Label reqDateTxt = new Label(String.valueOf(resultSet.getDate("RequestDate")));
            fullNamelbl.getStyleClass().add("labels");
            fullNameTxt.getStyleClass().add("labels");
            reqTypelbl.getStyleClass().add("labels");
            reqTypeTxt.getStyleClass().add("labels");
            selectAcclbl.getStyleClass().add("labels");
            selectAccTxt.getStyleClass().add("labels");
            chequeNumlbl.getStyleClass().add("labels");
            chequeNumTxt.getStyleClass().add("labels");
            reqDatelbl.getStyleClass().add("labels");
            reqDateTxt.getStyleClass().add("labels");
            VBox vBox1 = new VBox(5);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            VBox vBox2 = new VBox(5);
            vBox1.getChildren().addAll(fullNamelbl , reqTypelbl , selectAcclbl , chequeNumlbl , reqDatelbl);
            vBox2.getChildren().addAll(fullNameTxt , reqTypeTxt , selectAccTxt , chequeNumTxt , reqDateTxt);
            BorderPane borderPane = new BorderPane();
            borderPane.getStyleClass().add("CPBorderPane1");
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            mainVBox.getChildren().add(borderPane);
        }
    }
    public void ShowReceiptCheque(ResultSet resultSet) throws SQLException {
        mainPane.setPrefHeight(418);
        mainVBox.setPrefHeight(418);
        while (resultSet.next()){
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , resultSet.getInt("CustomerID"));
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next()){
                firstName = resultSet1.getNString("FirstName");
                lastName = resultSet1.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , resultSet.getInt("AccountID"));
            ResultSet resultSet2 = statement1.executeQuery();
            while (resultSet2.next()){
                accNum = resultSet2.getNString("AccountNumber");
            }
            Label fullNamelbl = new Label("نام و نام خانوادگی");
            Label fullNameTxt = new Label(firstName + " " + lastName);
            Label reqTypelbl = new Label("نوع درخواست :");
            Label reqTypeTxt = new Label("وصول چک");
            Label selectAcclbl = new Label("حساب انتخاب شده :");
            Label selectAccTxt = new Label(accNum);
            Label chequeSeriallbl = new Label("شماره سریال چک :");
            Label chequeSerialTxt = new Label(String.valueOf(resultSet.getLong("ChequeSerialNum")));
            Label chequeDatelbl = new Label("تاریخ وصول چک :");
            Label chequeDateTxt = new Label(String.valueOf(resultSet.getDate("ChequeDate")));
            Label chequeAmountlbl = new Label("مبلغ چک :");
            Label chequeAmountTxt = new Label(String.valueOf(resultSet.getLong("ChequeAmount")));
            Label reqDatelbl = new Label("تاریخ ثبت درخواست :");
            Label reqDateTxt = new Label(String.valueOf(resultSet.getDate("RequestDate")));
            fullNamelbl.getStyleClass().add("labels");
            fullNameTxt.getStyleClass().add("labels");
            reqTypelbl.getStyleClass().add("labels");
            reqTypeTxt.getStyleClass().add("labels");
            selectAcclbl.getStyleClass().add("labels");
            selectAccTxt.getStyleClass().add("labels");
            chequeSeriallbl.getStyleClass().add("labels");
            chequeSerialTxt.getStyleClass().add("labels");
            chequeDatelbl.getStyleClass().add("labels");
            chequeDateTxt.getStyleClass().add("labels");
            chequeAmountlbl.getStyleClass().add("labels");
            chequeAmountTxt.getStyleClass().add("labels");
            reqDatelbl.getStyleClass().add("labels");
            reqDateTxt.getStyleClass().add("labels");
            VBox vBox1 = new VBox(7);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            VBox vBox2 = new VBox(7);
            vBox1.getChildren().addAll(fullNamelbl , reqTypelbl , selectAcclbl , chequeSeriallbl , chequeDatelbl , chequeAmountlbl , reqDatelbl);
            vBox2.getChildren().addAll(fullNameTxt , reqTypeTxt , selectAccTxt , chequeSerialTxt , chequeDateTxt , chequeAmountTxt , reqDateTxt);
            BorderPane borderPane = new BorderPane();
            borderPane.getStyleClass().add("CPBorderPane1");
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            mainVBox.getChildren().add(borderPane);
        }
    }
}
