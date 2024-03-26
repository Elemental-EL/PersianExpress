package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.GharzolH;
import org.example.persianexpress.Objects.StockCheque;
import org.example.persianexpress.Objects.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ChequeIssuingController {
    private Stage stage;
    private Scene scene;
    @FXML
    private ImageView logoBtn,backBtn;
    @FXML
    private TextField nameText, nCodeText, amountText;
    @FXML
    private DatePicker date;
    @FXML
    private Button submitBtn;
    @FXML
    private ChoiceBox<String> accChoice;
    @FXML
    private Text errorText;
    private Connection connection;
    
    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-IQ6LNQ5;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "PEDB" , "pedb1234");
        String[] accs = StockCheque.getValidAccsWithCheque(connection);
        accChoice.getItems().addAll(accs);
        accChoice.setValue(accs[0]);
        if (accs.length<2){
            submitBtn.setDisable(true);
            errorText.setText("*شما حساب معتبری برای صدور چک ندارید.");
        }
    }



    @FXML
    void onLogoClicked(MouseEvent event){

    }
    @FXML
    void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
    @FXML
    void onSubmitClicked(ActionEvent event) throws SQLException, IOException {
        String accSlctd = accChoice.getSelectionModel().getSelectedItem();
        if (accSlctd.equals("انتخاب کنید")||nameText.getText().trim().isEmpty()||nCodeText.getText().trim().isEmpty()||amountText.getText().trim().isEmpty()) {
            errorText.setText("*پر کردن تمامی فیلد ها الزامی است.");
        }else if (!StockCheque.chequesLeft(connection,GharzolH.getAccountUID(accSlctd,connection))){
            errorText.setText("*شما برگه ای برای صدور چک برای این حساب ندارید.");
        } else if (!nCodeText.getText().matches("\\d+") || !amountText.getText().matches("\\d+")) {
            errorText.setText("*کد ملی و مبلغ باید فقط شامل عدد باشند.");
        } else {
            ResultSet resultSet = StockCheque.getValidUsersForIssuing(connection);
            boolean isValid = false;
            while (resultSet.next()){
                if (nameText.getText().equals(resultSet.getNString("FirstName")+" "+resultSet.getNString("LastName"))&&nCodeText.getText().equals(resultSet.getString("NationalCode"))){
                    isValid = true;
                }
            }
            if (!isValid){
                errorText.setText("*شخص معتبری برای صدور چک یافت نشد.");
            } else {
                long balance = GharzolH.getAccountBalance(accSlctd, connection);
                if (balance < Long.parseLong(amountText.getText())){
                    errorText.setText("*موجودی حساب شما کافی نمی باشد.");
                } else {
                    StockCheque.issueCheque(accSlctd, connection, nCodeText, amountText, date);
                    Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    stage.centerOnScreen();
                }
            }
        }
    }



}
