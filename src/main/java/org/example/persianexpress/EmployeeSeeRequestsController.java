package org.example.persianexpress;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import java.util.Objects;

public class EmployeeSeeRequestsController {
    private Stage stage;
    private Scene scene;
    @FXML
    private ChoiceBox<String> RequestTypeBox;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private VBox mainVBox;
    private Connection connection;
    public static int REQID = 100;
    public void initialize() throws SQLException {
        String[] REQType = new String[]{"همه درخواست ها" , "ایجاد حساب جدید" , "بستن حساب" , "کارت بانکی" , "وام" , "دسته چک" , "وصول چک"};
        RequestTypeBox.getItems().addAll(REQType);
        RequestTypeBox.setValue(REQType[0]);

        ArrayList<CreateAccReq> CreateAccREQ = new ArrayList<>();
        ArrayList<DeleteAccReq> DeleteAccREQ = new ArrayList<>();
        ArrayList<CardReq> CardREQ = new ArrayList<>();
        ArrayList<LoanReq> LoanREQ = new ArrayList<>();
        ArrayList<CheckBookReq> CheckBookREQ = new ArrayList<>();
        ArrayList<Receipt> ReceiptChequeREQ = new ArrayList<>();

        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        PreparedStatement statement1 = connection.prepareStatement("select *from CreateAccountREQ");
        ResultSet resultSet1 = statement1.executeQuery();
        while (resultSet1.next()){
            CreateAccReq accREQ = new CreateAccReq(resultSet1.getInt("RequestID") , resultSet1.getInt("CustomerID"));
            accREQ.setAccType(resultSet1.getNString("AccountType"));
            accREQ.setfName(resultSet1.getNString("FirstName"));
            accREQ.setlName(resultSet1.getNString("LastName"));
            accREQ.setnCode(resultSet1.getString("NationalCode"));
            accREQ.setbDate(resultSet1.getDate("BirthDate"));
            accREQ.setbPlace(resultSet1.getNString("BirthPlace"));
            accREQ.setPhNumber(resultSet1.getNString("PhoneNumber"));
            accREQ.sethPhNumber(resultSet1.getNString("HomePhoneNumber"));
            accREQ.sethAdress(resultSet1.getNString("HomeAddress"));
            accREQ.setReqDate(resultSet1.getDate("RequestDate"));
            CreateAccREQ.add(accREQ);
        }
        PreparedStatement statement2 = connection.prepareStatement("select *from DeleteAccountREQ");
        ResultSet resultSet2 = statement2.executeQuery();
        while (resultSet2.next()){
            DeleteAccReq accREQ = new DeleteAccReq(resultSet2.getInt("RequestID") , resultSet2.getInt("CustomerID"));
            accREQ.setAccID(resultSet2.getInt("AccountID"));
            accREQ.setSubstituteAccNum(resultSet2.getNString("SubstituteAccount"));
            accREQ.setReqDate(resultSet2.getDate("RequestDate"));
            DeleteAccREQ.add(accREQ);
        }
        PreparedStatement statement3 = connection.prepareStatement("select *from BankCardREQ");
        ResultSet resultSet3 = statement3.executeQuery();
        while (resultSet3.next()){
            CardReq cardReq = new CardReq(resultSet3.getInt("RequestID") , resultSet3.getInt("CustomerId"));
            cardReq.setAccID(resultSet3.getInt("SelectedAccountID"));
            cardReq.setReqDate(resultSet3.getDate("RequestDate"));
            CardREQ.add(cardReq);
        }
        PreparedStatement statement4 = connection.prepareStatement("select *from LoanREQ");
        ResultSet resultSet4 = statement4.executeQuery();
        while (resultSet4.next()){
            LoanReq loanReq = new LoanReq(resultSet4.getInt("RequestID") , resultSet4.getInt("CustomerID"));
            loanReq.setAccID(resultSet4.getInt("SelectedAccountID"));
            loanReq.setcDiploma(resultSet4.getNString("CustomerDiploma"));
            loanReq.setcJob(resultSet4.getNString("CustomerJob"));
            loanReq.setsFName(resultSet4.getNString("SuretyFirstName"));
            loanReq.setsLName(resultSet4.getNString("SuretyLastName"));
            loanReq.setsNCode(resultSet4.getString("SuretyNationalCode"));
            loanReq.setsFatherName(resultSet4.getNString("SuretyFatherName"));
            loanReq.setsBDate(resultSet4.getDate("SuretyBirthDate"));
            loanReq.setsBPlace(resultSet4.getNString("SuretyBirthPlace"));
            loanReq.setsPhNumber(resultSet4.getString("SuretyPhoneNumber"));
            loanReq.setsDiploma(resultSet4.getNString("SuretyDiploma"));
            loanReq.setsJob(resultSet4.getNString("SuretyJob"));
            loanReq.setLoanType(resultSet4.getNString("LoanType"));
            loanReq.setReqDate(resultSet4.getDate("RequestDate"));
            LoanREQ.add(loanReq);
        }
        PreparedStatement statement5 = connection.prepareStatement("select *from CheckBookREQ");
        ResultSet resultSet5 = statement5.executeQuery();
        while (resultSet5.next()){
            CheckBookReq checkBookReq = new CheckBookReq(resultSet5.getInt("RequestID") , resultSet5.getInt("CustomerID"));
            checkBookReq.setAccID(resultSet5.getInt("AccountID"));
            checkBookReq.setCheckNum(resultSet5.getInt("CheckNum"));
            checkBookReq.setReqDate(resultSet5.getDate("RequestDate"));
            CheckBookREQ.add(checkBookReq);
        }
        PreparedStatement statement6 = connection.prepareStatement("select *from ReceiptChequeREQ");
        ResultSet resultSet6 = statement6.executeQuery();
        while (resultSet6.next()){
            Receipt receipt = new Receipt(resultSet6.getInt("RequestID") , resultSet6.getInt("CustomerID"));
            receipt.setAccID(resultSet6.getInt("AccountID"));
            receipt.setChequeSerial(resultSet6.getLong("ChequeSerialNum"));
            receipt.setReqDate(resultSet6.getDate("RequestDate"));
            receipt.setChequeDate(resultSet6.getDate("ChequeDate"));
            receipt.setChequeAmount(resultSet6.getLong("ChequeAmount"));
            ReceiptChequeREQ.add(receipt);
        }

        ShowAllREQ(CreateAccREQ , DeleteAccREQ , CardREQ , LoanREQ , CheckBookREQ , ReceiptChequeREQ);
        RequestTypeBox.setOnAction(event -> {
            if (Objects.equals(RequestTypeBox.getValue(), "همه درخواست ها")){
                mainVBox.getChildren().clear();
                try {
                    ShowAllREQ(CreateAccREQ , DeleteAccREQ , CardREQ , LoanREQ , CheckBookREQ , ReceiptChequeREQ);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (Objects.equals(RequestTypeBox.getValue(), "ایجاد حساب جدید")) {
                mainVBox.getChildren().clear();
                ShowCreateAccREQ(CreateAccREQ);
            } else if (Objects.equals(RequestTypeBox.getValue(), "بستن حساب")) {
                mainVBox.getChildren().clear();
                try {
                    ShowDeleteAccREQ(DeleteAccREQ);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (Objects.equals(RequestTypeBox.getValue(), "کارت بانکی")) {
                mainVBox.getChildren().clear();
                try {
                    ShowCardREQ(CardREQ);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (Objects.equals(RequestTypeBox.getValue(), "وام")) {
                mainVBox.getChildren().clear();
                try {
                    ShowLoanREQ(LoanREQ);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (Objects.equals(RequestTypeBox.getValue(), "دسته چک")) {
                mainVBox.getChildren().clear();
                try {
                    ShowCheckBookREQ(CheckBookREQ);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (Objects.equals(RequestTypeBox.getValue(), "وصول چک")) {
                mainVBox.getChildren().clear();
                try {
                    ShowReceiptChequeREQ(ReceiptChequeREQ);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/EmployeePanel.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Persian Express");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    private void ShowAllREQ(ArrayList<CreateAccReq> CreateAccREQ , ArrayList<DeleteAccReq> DeleteAccREQ , ArrayList<CardReq> CardREQ , ArrayList<LoanReq> LoanREQ , ArrayList<CheckBookReq> CheckBookREQ , ArrayList<Receipt> ReceiptChequeREQ) throws SQLException {
        mainVBox.setPrefHeight(500);
        mainPane.setPrefHeight(500);
        int i = CreateAccREQ.size() + DeleteAccREQ.size() + CardREQ.size() +LoanREQ.size() + CheckBookREQ.size() + ReceiptChequeREQ.size();
        if (i > 2){
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 240*(i-2));
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 240*(i-2));
        }
        for (CreateAccReq createAcc: CreateAccREQ) {
            Label requestTypelbl = new Label("نوع درخواست :");
            Label requestTypetxt = new Label("ایجاد حساب جدید");
            Label selectedAccTypelbl = new Label("نوع حساب درخواستی :");
            Label selectedAccTypeTxt = new Label(createAcc.getAccType());
            Label userFNamelbl = new Label("نام و نام خانوادگی :");
            Label userFNametxt = new Label(createAcc.getfName() + " " + createAcc.getlName());
            Label requestDatelbl = new Label("تاریخ ثبت درخواست :");
            Label requestDatetxt = new Label(String.valueOf(createAcc.getReqDate()));
            Button seeRequest = new Button("مشاهده درخواست");
            requestTypelbl.getStyleClass().add("labels1");
            requestTypetxt.getStyleClass().add("labels1");
            requestDatelbl.getStyleClass().add("labels1");
            requestDatetxt.getStyleClass().add("labels1");
            selectedAccTypelbl.getStyleClass().add("labels1");
            selectedAccTypeTxt.getStyleClass().add("labels1");
            userFNametxt.getStyleClass().add("labels1");
            userFNamelbl.getStyleClass().add("labels1");
            seeRequest.getStyleClass().addAll("seeRequestBtn" , "zoom");
            VBox vBox1 = new VBox(4);
            VBox vBox2 = new VBox(4);
            vBox1.getChildren().addAll(requestTypelbl , selectedAccTypelbl , userFNamelbl , requestDatelbl );
            vBox2.getChildren().addAll(requestTypetxt , selectedAccTypeTxt , userFNametxt , requestDatetxt );
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            BorderPane borderPane1 = new BorderPane();
            borderPane1.setRight(vBox1);
            borderPane1.setLeft(vBox2);
            BorderPane borderPane2 = new BorderPane();
            BorderPane spacePane = new BorderPane();
            borderPane2.setCenter(seeRequest);
            borderPane1.getStyleClass().addAll("gray" , "CPBorderPane1");
            borderPane2.getStyleClass().addAll("gray" , "CPBorderPane1");
            spacePane.setStyle("-fx-pref-height: 20");
            seeRequest.setOnAction(event -> {
                REQID = createAcc.getReqID();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeOneRequest.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }
        for (DeleteAccReq deleteAcc: DeleteAccREQ) {
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , deleteAcc.getReqerID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                firstName = resultSet.getNString("FirstName");
                lastName = resultSet.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , deleteAcc.getAccID());
            ResultSet resultSet1 = statement1.executeQuery();
            while (resultSet1.next()){
                accNum = resultSet1.getNString("AccountNumber");
            }
            //
            Label requestTypelbl = new Label("نوع درخواست :");
            Label requestTypetxt = new Label("بستن حساب");
            Label userFNamelbl = new Label("نام و نام خانوادگی :");
            Label userFNameTxt = new Label(firstName + " " + lastName);
            Label firstAcclbl = new Label("حساب اصلی :");
            Label firstAccTxt = new Label(accNum);
            Label secondAcclbl = new Label("حساب جایگزین :");
            Label secondAccTxt = new Label(deleteAcc.getSubstituteAccNum());
            Label requestDatelbl = new Label("تاریخ ثبت درخواست :");
            Label requestDatetxt = new Label(String.valueOf(deleteAcc.getReqDate()));
            Button seeRequest = new Button("مشاهده درخواست");
            requestTypelbl.getStyleClass().add("labels1");
            requestTypetxt.getStyleClass().add("labels1");
            userFNamelbl.getStyleClass().add("labels1");
            userFNameTxt.getStyleClass().add("labels1");
            firstAcclbl.getStyleClass().add("labels1");
            firstAccTxt.getStyleClass().add("labels1");
            secondAcclbl.getStyleClass().add("labels1");
            secondAccTxt.getStyleClass().add("labels1");
            requestDatelbl.getStyleClass().add("labels1");
            requestDatetxt.getStyleClass().add("labels1");
            seeRequest.getStyleClass().addAll("seeRequestBtn" , "zoom");
            VBox vBox1 = new VBox(5);
            VBox vBox2 = new VBox(5);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox1.getChildren().addAll(requestTypelbl , userFNamelbl , firstAcclbl , secondAcclbl , requestDatelbl);
            vBox2.getChildren().addAll(requestTypetxt , userFNameTxt , firstAccTxt , secondAccTxt , requestDatetxt);
            BorderPane borderPane1 = new BorderPane();
            borderPane1.setRight(vBox1);
            borderPane1.setLeft(vBox2);
            BorderPane borderPane2 = new BorderPane();
            borderPane2.setCenter(seeRequest);
            BorderPane spacePane = new BorderPane();
            borderPane1.getStyleClass().addAll("gray" , "CPBorderPane1");
            borderPane2.getStyleClass().addAll("gray" , "CPBorderPane1");
            spacePane.setStyle("-fx-pref-height: 20");
            seeRequest.setOnAction(event -> {
                REQID = deleteAcc.getReqID();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeOneRequest.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }
        for (CardReq cardreq: CardREQ) {
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , cardreq.getReqerID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                firstName = resultSet.getNString("FirstName");
                lastName = resultSet.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , cardreq.getAccID());
            ResultSet resultSet1 = statement1.executeQuery();
            while (resultSet1.next()){
                accNum = resultSet1.getNString("AccountNumber");
            }
            //
            Label requestTypelbl = new Label("نوع درخواست :");
            Label requestTypetxt = new Label("درخواست کارت بانکی");
            Label userFNamelbl = new Label("نام و نام خانوادگی :");
            Label userFNameTxt = new Label(firstName + " " + lastName);
            Label accNumberlbl = new Label("حساب مبداء :");
            Label accNumbertxt = new Label(accNum);
            Label requestDatelbl = new Label("تاریخ ثبت درخواست :");
            Label requestDatetxt = new Label(String.valueOf(cardreq.getReqDate()));
            Button seeRequest = new Button("مشاهده درخواست");
            requestTypelbl.getStyleClass().add("labels1");
            requestTypetxt.getStyleClass().add("labels1");
            userFNamelbl.getStyleClass().add("labels1");
            userFNameTxt.getStyleClass().add("labels1");
            accNumberlbl.getStyleClass().add("labels1");
            accNumbertxt.getStyleClass().add("labels1");
            requestDatelbl.getStyleClass().add("labels1");
            requestDatetxt.getStyleClass().add("labels1");
            seeRequest.getStyleClass().addAll("seeRequestBtn" , "zoom");
            VBox vBox1 = new VBox(5);
            VBox vBox2 = new VBox(5);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox1.getChildren().addAll(requestTypelbl , userFNamelbl , accNumberlbl  , requestDatelbl);
            vBox2.getChildren().addAll(requestTypetxt , userFNameTxt , accNumbertxt  , requestDatetxt);
            BorderPane borderPane1 = new BorderPane();
            borderPane1.setRight(vBox1);
            borderPane1.setLeft(vBox2);
            BorderPane borderPane2 = new BorderPane();
            borderPane2.setCenter(seeRequest);
            BorderPane spacePane = new BorderPane();
            borderPane1.getStyleClass().addAll("gray" , "CPBorderPane1");
            borderPane2.getStyleClass().addAll("gray" , "CPBorderPane1");
            spacePane.setStyle("-fx-pref-height: 20");
            seeRequest.setOnAction(event -> {
                REQID = cardreq.getReqID();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeOneRequest.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }
        for (LoanReq loanreq: LoanREQ) {
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , loanreq.getReqerID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                firstName = resultSet.getNString("FirstName");
                lastName = resultSet.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , loanreq.getAccID());
            ResultSet resultSet1 = statement1.executeQuery();
            while (resultSet1.next()){
                accNum = resultSet1.getNString("AccountNumber");
            }
            Label requestTypelbl = new Label("نوع درخواست :");
            Label requestTypetxt = new Label("درخواست وام");
            Label userFNamelbl = new Label("نام و نام خانوادگی :");
            Label userFNameTxt = new Label(firstName + " " + lastName);
            Label accNumberlbl = new Label("حساب مبداء :");
            Label accNumbertxt = new Label(accNum);
            Label loanTypelbl = new Label("نوع وام درخواستی :");
            Label loanTypeTxt = new Label(loanreq.getLoanType());
            Label requestDatelbl = new Label("تاریخ ثبت درخواست :");
            Label requestDatetxt = new Label(String.valueOf(loanreq.getReqDate()));
            Button seeRequest = new Button("مشاهده درخواست");
            requestTypelbl.getStyleClass().add("labels1");
            requestTypetxt.getStyleClass().add("labels1");
            userFNamelbl.getStyleClass().add("labels1");
            userFNameTxt.getStyleClass().add("labels1");
            accNumberlbl.getStyleClass().add("labels1");
            accNumbertxt.getStyleClass().add("labels1");
            loanTypelbl.getStyleClass().add("labels1");
            loanTypeTxt.getStyleClass().add("labels1");
            requestDatelbl.getStyleClass().add("labels1");
            requestDatetxt.getStyleClass().add("labels1");
            seeRequest.getStyleClass().addAll("seeRequestBtn" , "zoom");
            VBox vBox1 = new VBox(5);
            VBox vBox2 = new VBox(5);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox1.getChildren().addAll(requestTypelbl , userFNamelbl , accNumberlbl , loanTypelbl , requestDatelbl);
            vBox2.getChildren().addAll(requestTypetxt , userFNameTxt , accNumbertxt , loanTypeTxt , requestDatetxt);
            BorderPane borderPane1 = new BorderPane();
            borderPane1.setRight(vBox1);
            borderPane1.setLeft(vBox2);
            BorderPane borderPane2 = new BorderPane();
            borderPane2.setCenter(seeRequest);
            BorderPane spacePane = new BorderPane();
            borderPane1.getStyleClass().addAll("gray" , "CPBorderPane1");
            borderPane2.getStyleClass().addAll("gray" , "CPBorderPane1");
            spacePane.setStyle("-fx-pref-height: 20");
            seeRequest.setOnAction(event -> {
                REQID = loanreq.getReqID();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeOneRequest.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }
        for (CheckBookReq chequeReq: CheckBookREQ) {
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , chequeReq.getReqerID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                firstName = resultSet.getNString("FirstName");
                lastName = resultSet.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , chequeReq.getAccID());
            ResultSet resultSet1 = statement1.executeQuery();
            while (resultSet1.next()){
                accNum = resultSet1.getNString("AccountNumber");
            }
            Label requestTypelbl = new Label("نوع درخواست :");
            Label requestTypetxt = new Label("درخواست دسته چک");
            Label userFNamelbl = new Label("نام و نام خانوادگی :");
            Label userFNameTxt = new Label(firstName + " " + lastName);
            Label accNumberlbl = new Label("حساب مبداء :");
            Label accNumbertxt = new Label(accNum);
            Label requestDatelbl = new Label("تاریخ ثبت درخواست :");
            Label requestDatetxt = new Label(String.valueOf(chequeReq.getReqDate()));
            Button seeRequest = new Button("مشاهده درخواست");
            requestTypelbl.getStyleClass().add("labels1");
            requestTypetxt.getStyleClass().add("labels1");
            userFNamelbl.getStyleClass().add("labels1");
            userFNameTxt.getStyleClass().add("labels1");
            accNumberlbl.getStyleClass().add("labels1");
            accNumbertxt.getStyleClass().add("labels1");
            requestDatelbl.getStyleClass().add("labels1");
            requestDatetxt.getStyleClass().add("labels1");
            seeRequest.getStyleClass().addAll("seeRequestBtn" , "zoom");
            VBox vBox1 = new VBox(4);
            VBox vBox2 = new VBox(4);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox1.getChildren().addAll(requestTypelbl , userFNamelbl , accNumberlbl , requestDatelbl);
            vBox2.getChildren().addAll(requestTypetxt , userFNameTxt , accNumbertxt , requestDatetxt);
            BorderPane borderPane1 = new BorderPane();
            borderPane1.setRight(vBox1);
            borderPane1.setLeft(vBox2);
            BorderPane borderPane2 = new BorderPane();
            borderPane2.setCenter(seeRequest);
            BorderPane spacePane = new BorderPane();
            borderPane1.getStyleClass().addAll("gray" , "CPBorderPane1");
            borderPane2.getStyleClass().addAll("gray" , "CPBorderPane1");
            spacePane.setStyle("-fx-pref-height: 20");
            seeRequest.setOnAction(event -> {
                REQID = chequeReq.getReqID();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeOneRequest.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }
        for (Receipt receiptCheque:ReceiptChequeREQ) {
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , receiptCheque.getReqerID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                firstName = resultSet.getNString("FirstName");
                lastName = resultSet.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , receiptCheque.getAccID());
            ResultSet resultSet1 = statement1.executeQuery();
            while (resultSet1.next()){
                accNum = resultSet1.getNString("AccountNumber");
            }
            Label requestTypelbl = new Label("نوع درخواست :");
            Label requestTypetxt = new Label("درخواست وصول چک :");
            Label userFNamelbl = new Label("نام و نام خانوادگی :");
            Label userFNameTxt = new Label(firstName + " " + lastName);
            Label chequeSerialNumlbl = new Label("شماره سریال چک :");
            Label chequeSerialNumTxt = new Label(String.valueOf(receiptCheque.getChequeSerial()));
            Label chequeAmountlbl = new Label("مبلغ چک :");
            Label chequeAmountTxt = new Label(String.valueOf(receiptCheque.getChequeAmount()));
            Label accNumberlbl = new Label("حساب مقصد :");
            Label accNumbertxt = new Label(accNum);
            Label requestDatelbl = new Label("تاریخ ثبت درخواست :");
            Label requestDatetxt = new Label(String.valueOf(receiptCheque.getReqDate()));
            Button seeRequest = new Button("مشاهده درخواست");
            requestTypelbl.getStyleClass().add("labels1");
            requestTypetxt.getStyleClass().add("labels1");
            userFNamelbl.getStyleClass().add("labels1");
            userFNameTxt.getStyleClass().add("labels1");
            chequeSerialNumlbl.getStyleClass().add("labels1");
            chequeSerialNumTxt.getStyleClass().add("labels1");
            chequeAmountlbl.getStyleClass().add("labels1");
            chequeAmountTxt.getStyleClass().add("labels1");
            accNumberlbl.getStyleClass().add("labels1");
            accNumbertxt.getStyleClass().add("labels1");
            requestDatelbl.getStyleClass().add("labels1");
            requestDatetxt.getStyleClass().add("labels1");
            seeRequest.getStyleClass().addAll("seeRequestBtn" , "zoom");
            VBox vBox1 = new VBox(6);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            VBox vBox2 = new VBox(6);
            vBox1.getChildren().addAll(requestTypelbl , userFNamelbl , chequeSerialNumlbl , chequeAmountlbl , accNumberlbl , requestDatelbl);
            vBox2.getChildren().addAll(requestTypetxt , userFNameTxt , chequeSerialNumTxt , chequeAmountTxt , accNumbertxt , requestDatetxt);
            BorderPane borderPane1 = new BorderPane();
            BorderPane borderPane2 = new BorderPane();
            BorderPane spacePane = new BorderPane();
            borderPane1.setRight(vBox1);
            borderPane1.setLeft(vBox2);
            borderPane2.setCenter(seeRequest);
            borderPane1.getStyleClass().addAll("gray" , "CPBorderPane1");
            borderPane2.getStyleClass().addAll("gray" , "CPBorderPane1");
            spacePane.setStyle("-fx-pref-height: 20");
            seeRequest.setOnAction(event -> {
                REQID = receiptCheque.getReqID();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeOneRequest.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }
    }
    private void ShowCreateAccREQ(ArrayList<CreateAccReq> CreateAccREQ){
        mainPane.setPrefHeight(500);
        mainVBox.setPrefHeight(500);
        int i = CreateAccREQ.size();
        if (i > 2){
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 240*(i-2));
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 240*(i-2));
        }
        for (CreateAccReq createAcc: CreateAccREQ) {
            Label requestTypelbl = new Label("نوع درخواست :");
            Label requestTypetxt = new Label("ایجاد حساب جدید");
            Label selectedAccTypelbl = new Label("نوع حساب درخواستی :");
            Label selectedAccTypeTxt = new Label(createAcc.getAccType());
            Label userFNamelbl = new Label("نام و نام خانوادگی :");
            Label userFNametxt = new Label(createAcc.getfName() + " " + createAcc.getlName());
            Label requestDatelbl = new Label("تاریخ ثبت درخواست :");
            Label requestDatetxt = new Label(String.valueOf(createAcc.getReqDate()));
            Button seeRequest = new Button("مشاهده درخواست");
            requestTypelbl.getStyleClass().add("labels1");
            requestTypetxt.getStyleClass().add("labels1");
            requestDatelbl.getStyleClass().add("labels1");
            requestDatetxt.getStyleClass().add("labels1");
            selectedAccTypelbl.getStyleClass().add("labels1");
            selectedAccTypeTxt.getStyleClass().add("labels1");
            userFNametxt.getStyleClass().add("labels1");
            userFNamelbl.getStyleClass().add("labels1");
            seeRequest.getStyleClass().addAll("seeRequestBtn" , "zoom");
            VBox vBox1 = new VBox(4);
            VBox vBox2 = new VBox(4);
            vBox1.getChildren().addAll(requestTypelbl , selectedAccTypelbl , userFNamelbl , requestDatelbl );
            vBox2.getChildren().addAll(requestTypetxt , selectedAccTypeTxt , userFNametxt , requestDatetxt );
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            BorderPane borderPane1 = new BorderPane();
            borderPane1.setRight(vBox1);
            borderPane1.setLeft(vBox2);
            BorderPane borderPane2 = new BorderPane();
            BorderPane spacePane = new BorderPane();
            borderPane2.setCenter(seeRequest);
            borderPane1.getStyleClass().addAll("gray" , "CPBorderPane1");
            borderPane2.getStyleClass().addAll("gray" , "CPBorderPane1");
            spacePane.setStyle("-fx-pref-height: 20");
            seeRequest.setOnAction(event -> {
                REQID = createAcc.getReqID();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeOneRequest.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }
    }
    private void ShowDeleteAccREQ(ArrayList<DeleteAccReq> DeleteAccREQ) throws SQLException {
        mainPane.setPrefHeight(500);
        mainVBox.setPrefHeight(500);
        int i = DeleteAccREQ.size();
        if (i > 2){
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 240*(i-2));
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 240*(i-2));
        }
        for (DeleteAccReq deleteAcc: DeleteAccREQ) {
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , deleteAcc.getReqerID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                firstName = resultSet.getNString("FirstName");
                lastName = resultSet.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , deleteAcc.getAccID());
            ResultSet resultSet1 = statement1.executeQuery();
            while (resultSet1.next()){
                accNum = resultSet1.getNString("AccountNumber");
            }
            //
            Label requestTypelbl = new Label("نوع درخواست :");
            Label requestTypetxt = new Label("بستن حساب");
            Label userFNamelbl = new Label("نام و نام خانوادگی :");
            Label userFNameTxt = new Label(firstName + " " + lastName);
            Label firstAcclbl = new Label("حساب اصلی :");
            Label firstAccTxt = new Label(accNum);
            Label secondAcclbl = new Label("حساب جایگزین :");
            Label secondAccTxt = new Label(deleteAcc.getSubstituteAccNum());
            Label requestDatelbl = new Label("تاریخ ثبت درخواست :");
            Label requestDatetxt = new Label(String.valueOf(deleteAcc.getReqDate()));
            Button seeRequest = new Button("مشاهده درخواست");
            requestTypelbl.getStyleClass().add("labels1");
            requestTypetxt.getStyleClass().add("labels1");
            userFNamelbl.getStyleClass().add("labels1");
            userFNameTxt.getStyleClass().add("labels1");
            firstAcclbl.getStyleClass().add("labels1");
            firstAccTxt.getStyleClass().add("labels1");
            secondAcclbl.getStyleClass().add("labels1");
            secondAccTxt.getStyleClass().add("labels1");
            requestDatelbl.getStyleClass().add("labels1");
            requestDatetxt.getStyleClass().add("labels1");
            seeRequest.getStyleClass().addAll("seeRequestBtn" , "zoom");
            VBox vBox1 = new VBox(5);
            VBox vBox2 = new VBox(5);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox1.getChildren().addAll(requestTypelbl , userFNamelbl , firstAcclbl , secondAcclbl , requestDatelbl);
            vBox2.getChildren().addAll(requestTypetxt , userFNameTxt , firstAccTxt , secondAccTxt , requestDatetxt);
            BorderPane borderPane1 = new BorderPane();
            borderPane1.setRight(vBox1);
            borderPane1.setLeft(vBox2);
            BorderPane borderPane2 = new BorderPane();
            borderPane2.setCenter(seeRequest);
            BorderPane spacePane = new BorderPane();
            borderPane1.getStyleClass().addAll("gray" , "CPBorderPane1");
            borderPane2.getStyleClass().addAll("gray" , "CPBorderPane1");
            spacePane.setStyle("-fx-pref-height: 20");
            seeRequest.setOnAction(event -> {
                REQID = deleteAcc.getReqID();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeOneRequest.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }
    }
    private void ShowCardREQ(ArrayList<CardReq> CardREQ) throws SQLException {
        mainPane.setPrefHeight(500);
        mainVBox.setPrefHeight(500);
        int i = CardREQ.size();
        if (i > 2){
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 240*(i-2));
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 240*(i-2));
        }
        for (CardReq cardreq: CardREQ) {
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , cardreq.getReqerID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                firstName = resultSet.getNString("FirstName");
                lastName = resultSet.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , cardreq.getAccID());
            ResultSet resultSet1 = statement1.executeQuery();
            while (resultSet1.next()){
                accNum = resultSet1.getNString("AccountNumber");
            }
            //
            Label requestTypelbl = new Label("نوع درخواست :");
            Label requestTypetxt = new Label("درخواست کارت بانکی");
            Label userFNamelbl = new Label("نام و نام خانوادگی :");
            Label userFNameTxt = new Label(firstName + " " + lastName);
            Label accNumberlbl = new Label("حساب مبداء :");
            Label accNumbertxt = new Label(accNum);
            Label requestDatelbl = new Label("تاریخ ثبت درخواست :");
            Label requestDatetxt = new Label(String.valueOf(cardreq.getReqDate()));
            Button seeRequest = new Button("مشاهده درخواست");
            requestTypelbl.getStyleClass().add("labels1");
            requestTypetxt.getStyleClass().add("labels1");
            userFNamelbl.getStyleClass().add("labels1");
            userFNameTxt.getStyleClass().add("labels1");
            accNumberlbl.getStyleClass().add("labels1");
            accNumbertxt.getStyleClass().add("labels1");
            requestDatelbl.getStyleClass().add("labels1");
            requestDatetxt.getStyleClass().add("labels1");
            seeRequest.getStyleClass().addAll("seeRequestBtn" , "zoom");
            VBox vBox1 = new VBox(5);
            VBox vBox2 = new VBox(5);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox1.getChildren().addAll(requestTypelbl , userFNamelbl , accNumberlbl  , requestDatelbl);
            vBox2.getChildren().addAll(requestTypetxt , userFNameTxt , accNumbertxt  , requestDatetxt);
            BorderPane borderPane1 = new BorderPane();
            borderPane1.setRight(vBox1);
            borderPane1.setLeft(vBox2);
            BorderPane borderPane2 = new BorderPane();
            borderPane2.setCenter(seeRequest);
            BorderPane spacePane = new BorderPane();
            borderPane1.getStyleClass().addAll("gray" , "CPBorderPane1");
            borderPane2.getStyleClass().addAll("gray" , "CPBorderPane1");
            spacePane.setStyle("-fx-pref-height: 20");
            seeRequest.setOnAction(event -> {
                REQID = cardreq.getReqID();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeOneRequest.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }
    }
    private void ShowLoanREQ(ArrayList<LoanReq> LoanREQ) throws SQLException {
        mainPane.setPrefHeight(500);
        mainVBox.setPrefHeight(500);
        int i = LoanREQ.size();
        if (i > 2){
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 240*(i-2));
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 240*(i-2));
        }
        for (LoanReq loanreq: LoanREQ) {
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , loanreq.getReqerID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                firstName = resultSet.getNString("FirstName");
                lastName = resultSet.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , loanreq.getAccID());
            ResultSet resultSet1 = statement1.executeQuery();
            while (resultSet1.next()){
                accNum = resultSet1.getNString("AccountNumber");
            }
            Label requestTypelbl = new Label("نوع درخواست :");
            Label requestTypetxt = new Label("درخواست وام");
            Label userFNamelbl = new Label("نام و نام خانوادگی :");
            Label userFNameTxt = new Label(firstName + " " + lastName);
            Label accNumberlbl = new Label("حساب مبداء :");
            Label accNumbertxt = new Label(accNum);
            Label loanTypelbl = new Label("نوع وام درخواستی :");
            Label loanTypeTxt = new Label(loanreq.getLoanType());
            Label requestDatelbl = new Label("تاریخ ثبت درخواست :");
            Label requestDatetxt = new Label(String.valueOf(loanreq.getReqDate()));
            Button seeRequest = new Button("مشاهده درخواست");
            requestTypelbl.getStyleClass().add("labels1");
            requestTypetxt.getStyleClass().add("labels1");
            userFNamelbl.getStyleClass().add("labels1");
            userFNameTxt.getStyleClass().add("labels1");
            accNumberlbl.getStyleClass().add("labels1");
            accNumbertxt.getStyleClass().add("labels1");
            loanTypelbl.getStyleClass().add("labels1");
            loanTypeTxt.getStyleClass().add("labels1");
            requestDatelbl.getStyleClass().add("labels1");
            requestDatetxt.getStyleClass().add("labels1");
            seeRequest.getStyleClass().addAll("seeRequestBtn" , "zoom");
            VBox vBox1 = new VBox(5);
            VBox vBox2 = new VBox(5);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox1.getChildren().addAll(requestTypelbl , userFNamelbl , accNumberlbl , loanTypelbl , requestDatelbl);
            vBox2.getChildren().addAll(requestTypetxt , userFNameTxt , accNumbertxt , loanTypeTxt , requestDatetxt);
            BorderPane borderPane1 = new BorderPane();
            borderPane1.setRight(vBox1);
            borderPane1.setLeft(vBox2);
            BorderPane borderPane2 = new BorderPane();
            borderPane2.setCenter(seeRequest);
            BorderPane spacePane = new BorderPane();
            borderPane1.getStyleClass().addAll("gray" , "CPBorderPane1");
            borderPane2.getStyleClass().addAll("gray" , "CPBorderPane1");
            spacePane.setStyle("-fx-pref-height: 20");
            seeRequest.setOnAction(event -> {
                REQID = loanreq.getReqID();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeOneRequest.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }

    }
    private void ShowCheckBookREQ(ArrayList<CheckBookReq> CheckBookREQ) throws SQLException {
        mainPane.setPrefHeight(500);
        mainVBox.setPrefHeight(500);
        int i = CheckBookREQ.size();
        if (i > 2){
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 240*(i-2));
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 240*(i-2));
        }
        for (CheckBookReq chequeReq: CheckBookREQ) {
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , chequeReq.getReqerID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                firstName = resultSet.getNString("FirstName");
                lastName = resultSet.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , chequeReq.getAccID());
            ResultSet resultSet1 = statement1.executeQuery();
            while (resultSet1.next()){
                accNum = resultSet1.getNString("AccountNumber");
            }
            Label requestTypelbl = new Label("نوع درخواست :");
            Label requestTypetxt = new Label("درخواست دسته چک");
            Label userFNamelbl = new Label("نام و نام خانوادگی :");
            Label userFNameTxt = new Label(firstName + " " + lastName);
            Label accNumberlbl = new Label("حساب مبداء :");
            Label accNumbertxt = new Label(accNum);
            Label requestDatelbl = new Label("تاریخ ثبت درخواست :");
            Label requestDatetxt = new Label(String.valueOf(chequeReq.getReqDate()));
            Button seeRequest = new Button("مشاهده درخواست");
            requestTypelbl.getStyleClass().add("labels1");
            requestTypetxt.getStyleClass().add("labels1");
            userFNamelbl.getStyleClass().add("labels1");
            userFNameTxt.getStyleClass().add("labels1");
            accNumberlbl.getStyleClass().add("labels1");
            accNumbertxt.getStyleClass().add("labels1");
            requestDatelbl.getStyleClass().add("labels1");
            requestDatetxt.getStyleClass().add("labels1");
            seeRequest.getStyleClass().addAll("seeRequestBtn" , "zoom");
            VBox vBox1 = new VBox(4);
            VBox vBox2 = new VBox(4);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox1.getChildren().addAll(requestTypelbl , userFNamelbl , accNumberlbl , requestDatelbl);
            vBox2.getChildren().addAll(requestTypetxt , userFNameTxt , accNumbertxt , requestDatetxt);
            BorderPane borderPane1 = new BorderPane();
            borderPane1.setRight(vBox1);
            borderPane1.setLeft(vBox2);
            BorderPane borderPane2 = new BorderPane();
            borderPane2.setCenter(seeRequest);
            BorderPane spacePane = new BorderPane();
            borderPane1.getStyleClass().addAll("gray" , "CPBorderPane1");
            borderPane2.getStyleClass().addAll("gray" , "CPBorderPane1");
            spacePane.setStyle("-fx-pref-height: 20");
            seeRequest.setOnAction(event -> {
                REQID = chequeReq.getReqID();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeOneRequest.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }
    }
    private void ShowReceiptChequeREQ(ArrayList<Receipt> ReceiptChequeREQ) throws SQLException {
        mainPane.setPrefHeight(500);
        mainVBox.setPrefHeight(500);
        int i = ReceiptChequeREQ.size();
        if (i > 2){
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 240*(i-2));
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 240*(i-2));
        }
        for (Receipt receiptCheque:ReceiptChequeREQ) {
            String firstName = new String();
            String lastName = new String();
            PreparedStatement statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , receiptCheque.getReqerID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                firstName = resultSet.getNString("FirstName");
                lastName = resultSet.getNString("LastName");
            }
            String accNum = new String();
            PreparedStatement statement1 = connection.prepareStatement("select AccountNumber from BankAccounts where AccountID = ? ");
            statement1.setInt(1 , receiptCheque.getAccID());
            ResultSet resultSet1 = statement1.executeQuery();
            while (resultSet1.next()){
                accNum = resultSet1.getNString("AccountNumber");
            }
            Label requestTypelbl = new Label("نوع درخواست :");
            Label requestTypetxt = new Label("درخواست وصول چک :");
            Label userFNamelbl = new Label("نام و نام خانوادگی :");
            Label userFNameTxt = new Label(firstName + " " + lastName);
            Label chequeSerialNumlbl = new Label("شماره سریال چک :");
            Label chequeSerialNumTxt = new Label(String.valueOf(receiptCheque.getChequeSerial()));
            Label chequeAmountlbl = new Label("مبلغ چک :");
            Label chequeAmountTxt = new Label(String.valueOf(receiptCheque.getChequeAmount()));
            Label accNumberlbl = new Label("حساب مقصد :");
            Label accNumbertxt = new Label(accNum);
            Label requestDatelbl = new Label("تاریخ ثبت درخواست :");
            Label requestDatetxt = new Label(String.valueOf(receiptCheque.getReqDate()));
            Button seeRequest = new Button("مشاهده درخواست");
            requestTypelbl.getStyleClass().add("labels1");
            requestTypetxt.getStyleClass().add("labels1");
            userFNamelbl.getStyleClass().add("labels1");
            userFNameTxt.getStyleClass().add("labels1");
            chequeSerialNumlbl.getStyleClass().add("labels1");
            chequeSerialNumTxt.getStyleClass().add("labels1");
            chequeAmountlbl.getStyleClass().add("labels1");
            chequeAmountTxt.getStyleClass().add("labels1");
            accNumberlbl.getStyleClass().add("labels1");
            accNumbertxt.getStyleClass().add("labels1");
            requestDatelbl.getStyleClass().add("labels1");
            requestDatetxt.getStyleClass().add("labels1");
            seeRequest.getStyleClass().addAll("seeRequestBtn" , "zoom");
            VBox vBox1 = new VBox(6);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            VBox vBox2 = new VBox(6);
            vBox1.getChildren().addAll(requestTypelbl , userFNamelbl , chequeSerialNumlbl , chequeAmountlbl , accNumberlbl , requestDatelbl);
            vBox2.getChildren().addAll(requestTypetxt , userFNameTxt , chequeSerialNumTxt , chequeAmountTxt , accNumbertxt , requestDatetxt);
            BorderPane borderPane1 = new BorderPane();
            BorderPane borderPane2 = new BorderPane();
            BorderPane spacePane = new BorderPane();
            borderPane1.setRight(vBox1);
            borderPane1.setLeft(vBox2);
            borderPane2.setCenter(seeRequest);
            borderPane1.getStyleClass().addAll("gray" , "CPBorderPane1");
            borderPane2.getStyleClass().addAll("gray" , "CPBorderPane1");
            spacePane.setStyle("-fx-pref-height: 20");
            seeRequest.setOnAction(event -> {
                REQID = receiptCheque.getReqID();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeOneRequest.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }

    }
}
