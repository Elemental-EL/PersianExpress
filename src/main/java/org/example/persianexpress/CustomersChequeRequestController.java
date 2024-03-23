package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.CheckBookReq;
import org.example.persianexpress.Objects.GharzolH;
import org.example.persianexpress.Objects.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomersChequeRequestController {
    private Stage stage;
    private Scene scene;
    @FXML
    private ChoiceBox<String> selectedAccount , numberOfCheque;
    @FXML
    private TextField firstNameText , familyNameText , nCodeText , fatherNameText , bPlaceText , codePText , mPhoneText , hPhoneText;
    @FXML
    private DatePicker bDate;
    @FXML
    private TextArea addressText;
    @FXML
    private Button submitBtn;
    @FXML
    private Text errorText;
    private Connection connection;
    private User user;
    private LocalDate nowsDate = LocalDate.now();

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-0KSSE4QN;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "Nasimi" , "138374");
        String[] numbers = new String[]{"انتخاب کنید" , "10" , "20" , "30" ,"40" , "50"};
        numberOfCheque.getItems().addAll(numbers);
        numberOfCheque.setValue(numbers[0]);
        String[] accNums = GharzolH.getValidAccNumsForCheque(connection);
        selectedAccount.getItems().addAll(accNums);
        selectedAccount.setValue(accNums[0]);
        user = User.createUserObj(connection,HelloController.userID);
        firstNameText.setText(user.getfName());
        firstNameText.setEditable(false);
        familyNameText.setText(user.getLastname());
        familyNameText.setEditable(false);
        nCodeText.setText(user.getNationalCode());
        nCodeText.setEditable(false);
        fatherNameText.setText(user.getFatherName());
        fatherNameText.setEditable(false);
        bDate.setValue(LocalDate.parse((CharSequence) user.getbDate()));
        bDate.setEditable(false);
        bPlaceText.setText(user.getbPlace());
        bPlaceText.setEditable(false);
        codePText.setText(user.getpCode());
        codePText.setEditable(false);
        mPhoneText.setText(user.getPhNumber());
        mPhoneText.setEditable(false);
        hPhoneText.setText(user.gethPhNumber());
        hPhoneText.setEditable(false);
        addressText.setText(user.gethAddress());
        addressText.setEditable(false);
        if (accNums.length<2){
            submitBtn.setDisable(true);
            errorText.setText("*شما حساب معتبری برای درخواست دسته چک ندارید.");
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

    public void onSubmitClicked(ActionEvent event) throws SQLException, IOException {
        String amountSlctd = numberOfCheque.getSelectionModel().getSelectedItem();
        String accSlctd = selectedAccount.getSelectionModel().getSelectedItem();
        if (accSlctd.equals("انتخاب کنید")){
            errorText.setText("*لطفا حساب مورد نظر را انتخاب کنید.");
        } else if (amountSlctd.equals("انتخاب کنید")){
            errorText.setText("لطفا تعداد برگه های چک را انتخاب کنید.");
        } else {
            CheckBookReq.submitChequeBookReq(accSlctd, amountSlctd, connection, user, nowsDate);
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
