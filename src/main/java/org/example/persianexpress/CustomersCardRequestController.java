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
import org.example.persianexpress.Objects.CardReq;
import org.example.persianexpress.Objects.CheckBookReq;
import org.example.persianexpress.Objects.GharzolH;
import org.example.persianexpress.Objects.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class CustomersCardRequestController {
    private Stage stage;
    private Scene scene;
    @FXML
    private ChoiceBox<String> selectedAccount;
    @FXML
    private TextField firstNameText, familyNameText, nCodeText, fatherNameText, bPlaceText, codePText, mPhoneText, hPhoneText;
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
        String[] accNums = GharzolH.getValidAccNumsForCard(connection);
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
            errorText.setText("*شما حساب معتبری برای درخواست کارت بانکی ندارید.");
        }
    }

    public void onLogoClicked(MouseEvent event) {
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
        String accSlctd = selectedAccount.getSelectionModel().getSelectedItem();
        if (accSlctd.equals("انتخاب کنید")){
            errorText.setText("*لطفا حساب مورد نظر را انتخاب کنید.");
        } else {
            CardReq.submitCardReq(accSlctd,connection,user,nowsDate);
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
