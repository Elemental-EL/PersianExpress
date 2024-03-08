package org.example.persianexpress.Controllers.Customers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CustomersCardRequestController {
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

    public void onBackClicked(MouseEvent event) {
    }

    public void onSubmitClicked(ActionEvent event) {
    }
}
