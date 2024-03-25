package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class CustomersSeeInfoController {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField firstNameText , familyNameText , nCodeText , fatherNameText , bPlaceText , codePText , mPhoneText , hPhoneText;
    @FXML
    private TextArea addressText;
    @FXML
    private DatePicker bDate;
    @FXML
    private Label errorTXT;
    private Connection connection;

    public void initialize() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-0KSSE4QN;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "Nasimi" , "138374" );
        PreparedStatement statement = connection.prepareStatement("select *from CustomersInfo where CustomerID = ?");
        statement.setInt(1 , HelloController.userID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            firstNameText.setText(resultSet.getNString("FirstName"));
            familyNameText.setText(resultSet.getNString("LastName"));
            nCodeText.setText(resultSet.getString("NationalCode"));
            fatherNameText.setText(resultSet.getNString("FatherName"));
            bPlaceText.setText(resultSet.getNString("BirthPlace"));
            codePText.setText(resultSet.getNString("PostCode"));
            mPhoneText.setText(resultSet.getNString("PhoneNumber"));
            hPhoneText.setText(resultSet.getNString("HomePhoneNumber"));
            addressText.setText(resultSet.getNString("HomeAddress"));
            bDate.setValue(resultSet.getDate("BirthDate").toLocalDate());
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

    public void onChangeInfoClicked(ActionEvent event) {
        firstNameText.setEditable(true);
        familyNameText.setEditable(true);
        nCodeText.setEditable(true);
        fatherNameText.setEditable(true);
        bPlaceText.setEditable(true);
        codePText.setEditable(true);
        mPhoneText.setEditable(true);
        hPhoneText.setEditable(true);
        addressText.setEditable(true);
        bDate.setEditable(true);
    }

    public void onRecordChangesClicked(ActionEvent event) throws SQLException {
        if (firstNameText.getText().isEmpty() || familyNameText.getText().isEmpty() || nCodeText.getText().isEmpty() || fatherNameText.getText().isEmpty() || bPlaceText.getText().isEmpty() || codePText.getText().isEmpty() || mPhoneText.getText().isEmpty() || hPhoneText.getText().isEmpty()){
            errorTXT.setText("وارد کردن همه اطلاعات الزامی میباشد!");
        }
        else if (!codePText.getText().matches("\\d{10}")){
            errorTXT.setText("*کد پستی باید ده رقمی باشد.");
        }
        else if (!mPhoneText.getText().matches("09\\d{9}")){
            errorTXT.setText("*فرمت شماره موبایل اشتباه است.");
        }
        else if (!hPhoneText.getText().matches("\\d{11}")){
            errorTXT.setText("*فرمت شماره تلفن ثابت اشتباه است.");
        }else {
            PreparedStatement statement = connection.prepareStatement("update CustomersInfo set FirstName = ? , LastName = ? , NationalCode = ? , BirthDate = ? , BirthPlace = ? , PhoneNumber = ? , HomePhoneNumber = ? , HomeAddress = ? , PostCode = ?  where CustomerID = ?");
            statement.setNString(1 , firstNameText.getText());
            statement.setNString(2 , familyNameText.getText());
            statement.setString(3 , nCodeText.getText());
            statement.setDate(4 , Date.valueOf(bDate.getValue()));
            statement.setNString(5 , bPlaceText.getText());
            statement.setNString(6 , mPhoneText.getText());
            statement.setNString(7 , hPhoneText.getText());
            statement.setNString(8 , addressText.getText());
            statement.setNString(9 , codePText.getText());
            statement.setInt(10 , HelloController.userID);
            int result = statement.executeUpdate();
            System.out.println(result);
        }
    }
}
