package org.example.persianexpress.Controllers.Customers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ReceiptViewController {
    @FXML
    private ImageView logoBtn,backBtn;
    @FXML
    private Label originAccNumTxt,originAccHolderTxt, destAccNumTxt,destAccHolderTxt, amountTxt,dateTxt,trackCode;


    @FXML
    void onLogoClicked(MouseEvent event){
    }
    @FXML
    void onBackClicked(MouseEvent event){
    }
    public void onPrintReceipt(MouseEvent event) {
    }
}