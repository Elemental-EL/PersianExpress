package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeSeeAccountsController {
    private Stage stage;
    private Scene scene;
    @FXML
    private Button addAccount;
    @FXML
    private ImageView backBtn;
    @FXML
    private ChoiceBox<String> showAccountType;
    public void initialize(){
        String[] accountType = new String[]{"همه حساب ها" , "قرض الحسنه جاری" , "قرض الحسنه سپرده" , "سپرده کوتاه مدت" , "سپرده بلند مدت"};
        showAccountType.getItems().addAll(accountType);
        showAccountType.setValue(accountType[0]);
    }
    @FXML
    private void onAddAccountClicked(ActionEvent event){

    }

    public void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/EmployeePanel.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Persian Express");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
}
