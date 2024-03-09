package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ChequeIssuingController {
    private Stage stage;
    private Scene scene;
    @FXML
    private ImageView logoBtn,backBtn;
    @FXML
    private Text nameText, nCodeText, amountText, dateText, cCodeText;
    @FXML
    private Button submitBtn;
    @FXML
    void onLogoClicked(MouseEvent event){

    }
    @FXML
    void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
    @FXML
    void onSubmitClicked(ActionEvent event){

    }
}
