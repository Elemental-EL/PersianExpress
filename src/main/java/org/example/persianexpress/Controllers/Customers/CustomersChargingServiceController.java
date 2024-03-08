package org.example.persianexpress.Controllers.Customers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CustomersChargingServiceController {
    @FXML
    private ChoiceBox<String> selectedAccount , Operator;
    @FXML
    private TextField phoneNumber , chargeAmount;

    public void initialize(){
        String[] operators = new String[]{"انتخاب کنید", "همراه اول" , "ایرانسل" , "رایتل"};
        Operator.getItems().addAll(operators);
        Operator.setValue(operators[0]);
    }
    public void onBackClicked(MouseEvent event) {
    }

    public void onBuyChargeClicked(ActionEvent event) {
    }
}
