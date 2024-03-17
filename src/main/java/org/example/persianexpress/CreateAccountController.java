package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;

public class CreateAccountController {
    private Stage stage;
    private Scene scene;
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
    @FXML
    private Text errorText;

    public void initialize(){
        String[] accountType = new String[]{"انتخاب کنید","قرض الحسنه جاری" , "قرض الحسنه سپرده" , "سپرده کوتاه مدت" , "سپرده مدت دار"};
        typeSlct.getItems().addAll(accountType);
        typeSlct.setValue(accountType[0]);
    }

    @FXML
    void onLogoClicked(MouseEvent event){

    }
    @FXML
    void onBackClicked(MouseEvent event) throws IOException {
        if (HelloController.userID == 100){
            Parent root = FXMLLoader.load(getClass().getResource("Pages/hello-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();
        }else if (HelloController.post == null){
            Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();
        }else if (HelloController.post.equals("کارمند")){
            Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/EmployeePanel.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();
        }else if (HelloController.post.equals("مدیر")){
            Parent root = FXMLLoader.load(getClass().getResource("Pages/Boss/BossPanel.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();
        }

    }
    @FXML
    void onSubmitClicked(ActionEvent event) throws SQLException {
        errorText.setText("");
        boolean isUnique = true;
        if (Objects.equals(typeSlct,"انتخاب کنید")||firstNameText.getText().trim().isEmpty() ||familyNameText.getText().trim().isEmpty() || nCodeText.getText().trim().isEmpty() || fatherNameText.getText().trim().isEmpty() || userText.getText().trim().isEmpty() || passText.getText().trim().isEmpty() || passRepText.getText().trim().isEmpty() || bPlaceText.getText().trim().isEmpty() || addressText.getText().trim().isEmpty() || codePText.getText().trim().isEmpty() || mPhoneText.getText().trim().isEmpty() || hPhoneText.getText().trim().isEmpty() || bDate.getValue()==null){
            errorText.setText("*پر کردن تمامی فیلد ها الزامی است.");
        }
        else if (passText.getText().length()<8){
            errorText.setText("*رمز عبور باید شامل حداقل 8 کاراکتر باشد.");
        }
        else if (!(Objects.equals(passText.getText(),passRepText.getText()))){
            errorText.setText("*تکرار رمز عبور اشتباه است.");
        }
        else if (!codePText.getText().matches("\\d+")){
            errorText.setText("*کد پستی باید تنها شامل عدد باشد.");
        }
        else if (!nCodeText.getText().matches("\\d+")){
            errorText.setText("*کد پستی باید تنها شامل عدد باشد.");
        }
        else if (!mPhoneText.getText().matches("\\d+")){
            errorText.setText("*کد پستی باید تنها شامل عدد باشد.");
        }
        else if (!hPhoneText.getText().matches("\\d+")){
            errorText.setText("*کد پستی باید تنها شامل عدد باشد.");
        }
        else if (!codePText.getText().matches("\\d{10}")){
            errorText.setText("*کد پستی باید ده رقمی باشد.");
        }
        else if (!mPhoneText.getText().matches("09\\d{9}")){
            errorText.setText("*فرمت شماره موبایل اشتباه است.");
        }
        else if (!hPhoneText.getText().matches("\\d{11}")){
            errorText.setText("*فرمت شماره تلفن ثابت اشتباه است.");
        }
        else if (!nCodeText.getText().matches("\\d{10}")){
            errorText.setText("*کد ملی باید ده رقمی باشد.");
        }
        else {
            String CustomerUserName = userText.getText();
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-IQ6LNQ5;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "PEDB" , "pedb1234");
            PreparedStatement statement0 = connection.prepareStatement("SELECT CustomerUN FROM CustomersInfo");
            ResultSet resultSet0 = statement0.executeQuery();
            while (resultSet0.next()){
                if (Objects.equals(CustomerUserName , resultSet0.getNString("CustomerUN"))){
                    isUnique = false;
                }
            }
            if (!isUnique){
                errorText.setText("*این نام کاربری قبلا استفاده شده است.");
            }
            else {
                Calendar calendar = Calendar.getInstance();
//                Date currentDate = calendar.getTime();
//                String currentDateTime = currentDate.toString();
                java.sql.Date currentDate = Date.valueOf(LocalDate.now());
                if (HelloController.post==null){
                    PreparedStatement statement1 = connection.prepareStatement("INSERT INTO CreateAccountREQ (RequestID,AccountType,CustomerUN,CustomerPassword,FirstName,LastName,NationalCode,BirthDate,BirthPlace,PhoneNumber,HomePhoneNumber,HomeAddress,PostCode,RequestDate,RequestStatus) VALUES (100003,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    statement1.setString(1,typeSlct.getValue());
                    statement1.setString(2,userText.getText());
                    statement1.setString(3,passText.getText());
                    statement1.setString(4,firstNameText.getText());
                    statement1.setString(5,familyNameText.getText());
                    statement1.setString(6,nCodeText.getText());
                    statement1.setDate(7, java.sql.Date.valueOf(bDate.getValue()));
                    statement1.setString(8,bPlaceText.getText());
                    statement1.setString(9,mPhoneText.getText());
                    statement1.setString(10,hPhoneText.getText());
                    statement1.setString(11,addressText.getText());
                    statement1.setString(12,codePText.getText());
                    statement1.setDate(13, currentDate);
                    statement1.setBoolean(14,false);
                    int resultSet1 = statement1.executeUpdate();
                    errorText.setText("SUCCESSFULL!!!");
                }
            }
            }
    }
}
