package org.example.persianexpress.Controllers.Customers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CustomersReceiptChequeController {
    @FXML
    private TextField firstName , nationalCode , chequeSerialNumber, chequeAmount , lastName;
    @FXML
    private DatePicker chequeDate;
    @FXML
    private ChoiceBox<String> selectedAccount;
    public void onBackClicked(MouseEvent event) {
    }

    public void onSumbitClicked(ActionEvent event) {
    }
}
