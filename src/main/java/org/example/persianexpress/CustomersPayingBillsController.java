package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomersPayingBillsController {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField billEarmark , paymentEarmark , billAmount;

    @FXML
    private ChoiceBox<String> SelectedAccount;
    @FXML
    private Label errorText;
    public void onBackclicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onPaymentClicked(ActionEvent event) {
        if (billEarmark.getText().isEmpty()||paymentEarmark.getText().isEmpty()||billAmount.getText().isEmpty()){
            errorText.setText("پر کردن تمامی فیلد ها الزامی است.");
        } else if (billAmount.getText().length()!=8||paymentEarmark.getText().length()!=8) {
            errorText.setText("شناسه قبض و شناسه پرداخت باید 8 رقمی باشند.");
        }
        else if (!billEarmark.getText().matches("\\d+")||!paymentEarmark.getText().matches("\\d+")||!billAmount.getText().matches("\\d+")){
            errorText.setText("شناسه قبض و شناسه پرداخت و مبلغ قبض فقط باید شامل عدد باشد.");
        } else if (!billEarmark.getText().matches("1403\\d{4}")) {
errorText.setText("فرمت شناسه قبض اشتباه است.");
        } else if (!paymentEarmark.getText().matches("1234\\d{4}")) {
   errorText.setText("فرمت شناسه پرداخت اشتباه است");
        }
    }
}
