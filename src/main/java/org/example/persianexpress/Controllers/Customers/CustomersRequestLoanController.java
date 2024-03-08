package org.example.persianexpress.Controllers.Customers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CustomersRequestLoanController {
    @FXML
    private TextField applicantFirstName , applicantFamily , applicantNCode , applicantFatherName , applicantBPlace , applicantDiploma , applicantPhoneNumber , applicantJob , suretyFirstName , suretyFamily , suretyNCode , suretyFatherName , suretyBPlace , suretyDiploma , suretyPhoneNumber , suretyJob;
    @FXML
    private DatePicker applicantBDate , suretyBDate ;
    @FXML
    private TextArea applicantTXT ;
    @FXML
    private ChoiceBox<String> loanType;
    public void onBackClicked(MouseEvent event) {
    }

    public void onRegisterClicked(ActionEvent event) {
    }
}
