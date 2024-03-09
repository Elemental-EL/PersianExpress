package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void onSubmitClicked(ActionEvent event) {
    }
}
