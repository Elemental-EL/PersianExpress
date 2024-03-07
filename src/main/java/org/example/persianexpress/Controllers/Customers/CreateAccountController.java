package org.example.persianexpress.Controllers.Customers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreateAccountController {
    @FXML
    private Button submitBtn;
    @FXML
    private ChoiceBox<String> typeSlct;
    @FXML
    private TextField firstNameText, familyNameText, nCodeText, fatherNameText, userText, bPlaceText, codePText, mPhoneText, hPhoneText;
    @FXML
    private DatePicker bDate;
    @FXML
    private TextArea addressText;
    @FXML
    private PasswordField passText, passRepText;
    @FXML
    private ImageView logoBtn, backBtn;

    public void initialize(){
        String[] accountType = new String[]{"انتخاب کنید","قرض الحسنه جاری" , "قرض الحسنه سپرده" , "سپرده کوتاه مدت" , "سپرده مدت دار"};
        typeSlct.getItems().addAll(accountType);
        typeSlct.setValue(accountType[0]);
    }

    @FXML
    void onLogoClicked(MouseEvent event){

    }
    @FXML
    void onBackClicked(MouseEvent event){

    }
    @FXML
    void onSubmitClicked(ActionEvent event){

    }
}
