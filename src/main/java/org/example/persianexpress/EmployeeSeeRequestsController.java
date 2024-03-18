package org.example.persianexpress;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class EmployeeSeeRequestsController {
    private Stage stage;
    private Scene scene;
    @FXML
    private ChoiceBox<String> RequestTypeBox;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private VBox mainVBox;

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

        Connection connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
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
            accREQ.setSubstituteAccID(resultSet2.getNString("SubstituteAccount"));
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

    private void ShowAllREQ(ArrayList<CreateAccReq> CreateAccREQ , ArrayList<DeleteAccReq> DeleteAccREQ , ArrayList<CardReq> CardREQ , ArrayList<LoanReq> LoanREQ , ArrayList<CheckBookReq> CheckBookREQ , ArrayList<Receipt> ReceiptChequeREQ){
        for (CreateAccReq createAcc: CreateAccREQ) {
            Label requestTypelbl = new Label("نوع درخواست :");
            Label requestTypetxt = new Label("ایجاد حساب جدید");
            Label selectedAccTypelbl = new Label("نوع حساب درخواستی :");
            Label selectedAccTypeTxt = new Label(createAcc.getAccType());
            Label userFNamelbl = new Label("نام و نام خانوادگی :");
            Label userFNametxt = new Label(createAcc.getfName() + " " + createAcc.getlName());
            Label requestDatelbl = new Label("تاریخ ثبت درخواست");
            Label requestDatetxt = new Label(String.valueOf(createAcc.getReqDate()));
            Button seeRequest = new Button("مشاهده درخواست");
            VBox vBox1 = new VBox(4);
            VBox vBox2 = new VBox(4);
            vBox1.getChildren().addAll(requestTypelbl , selectedAccTypelbl , userFNamelbl , requestDatelbl );
            vBox2.getChildren().addAll(requestTypetxt , selectedAccTypeTxt , userFNametxt , requestDatetxt );
            BorderPane borderPane1 = new BorderPane();
            borderPane1.setRight(vBox1);
            borderPane1.setLeft(vBox2);
            BorderPane borderPane2 = new BorderPane();
            borderPane2.setCenter(seeRequest);
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
            mainVBox.getChildren().add(borderPane1);
            mainVBox.getChildren().add(borderPane2);
        }
    }
}
