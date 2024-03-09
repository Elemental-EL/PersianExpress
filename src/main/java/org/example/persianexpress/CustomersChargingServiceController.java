package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomersChargingServiceController {
    private Stage stage;
    private Scene scene;
    @FXML
    private ChoiceBox<String> selectedAccount , Operator;
    @FXML
    private TextField phoneNumber , chargeAmount;

    public void initialize(){
        String[] operators = new String[]{"انتخاب کنید", "همراه اول" , "ایرانسل" , "رایتل"};
        Operator.getItems().addAll(operators);
        Operator.setValue(operators[0]);
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

    public void onBuyChargeClicked(ActionEvent event) {
    }
}
