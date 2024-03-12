package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;

public class SeeAccountsController {
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
    @FXML
    private void onBackBtnClicked(ActionEvent event){

    }
}
