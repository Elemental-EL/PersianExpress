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
import org.example.persianexpress.Objects.CreateAccReq;
import org.example.persianexpress.Objects.GharzolH;
import org.example.persianexpress.Objects.User;


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
    private User user;
    private java.sql.Date currentDate = Date.valueOf(LocalDate.now());
    private LocalDate nowsDate = LocalDate.now();
    private Connection connection;

    public void initialize() throws SQLException {
        String[] accountType = new String[]{"انتخاب کنید","قرض الحسنه جاری" , "قرض الحسنه سپرده" , "سپرده کوتاه مدت" , "سپرده مدت دار"};
        typeSlct.getItems().addAll(accountType);
        typeSlct.setValue(accountType[0]);
        connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-0KSSE4QN;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "Nasimi" , "138374");
        if (CustomersPanelController.loggedIn){
            user = User.createUserObj(connection,HelloController.userID);
            firstNameText.setText(user.getfName());
            firstNameText.setEditable(false);
            familyNameText.setText(user.getLastname());
            familyNameText.setEditable(false);
            nCodeText.setText(user.getNationalCode());
            nCodeText.setEditable(false);
            fatherNameText.setText(user.getFatherName());
            fatherNameText.setEditable(false);
            userText.setText(user.getUserName());
            userText.setEditable(false);
            passText.setText(user.getPassword());
            passText.setEditable(false);
            passRepText.setText(user.getPassword());
            passRepText.setEditable(false);
            bDate.setValue(LocalDate.parse((CharSequence) user.getbDate()));
            bDate.setEditable(false);
            bPlaceText.setText(user.getbPlace());
            bPlaceText.setEditable(false);
            codePText.setText(user.getpCode());
            codePText.setEditable(false);
            mPhoneText.setText(user.getPhNumber());
            mPhoneText.setEditable(false);
            hPhoneText.setText(user.gethPhNumber());
            hPhoneText.setEditable(false);
            addressText.setText(user.gethAddress());
            addressText.setEditable(false);
        }
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
    void onSubmitClicked(ActionEvent event) throws SQLException, IOException {
        String typeSlctd = typeSlct.getSelectionModel().getSelectedItem();
        errorText.setText("");
        boolean isUnique = true;
        boolean nIsUnique = true;
        if (Objects.equals(typeSlctd,"انتخاب کنید")||firstNameText.getText().trim().isEmpty() ||familyNameText.getText().trim().isEmpty() || nCodeText.getText().trim().isEmpty() || fatherNameText.getText().trim().isEmpty() || userText.getText().trim().isEmpty() || passText.getText().trim().isEmpty() || passRepText.getText().trim().isEmpty() || bPlaceText.getText().trim().isEmpty() || addressText.getText().trim().isEmpty() || codePText.getText().trim().isEmpty() || mPhoneText.getText().trim().isEmpty() || hPhoneText.getText().trim().isEmpty() || bDate.getValue()==null){
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
            errorText.setText("*کد ملی باید تنها شامل عدد باشد.");
        }
        else if (!mPhoneText.getText().matches("\\d+")){
            errorText.setText("*شماره موبایل باید تنها شامل عدد باشد.");
        }
        else if (!hPhoneText.getText().matches("\\d+")){
            errorText.setText("*شماره تلفن ثابت باید تنها شامل عدد باشد.");
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
            String CustomerNCode = nCodeText.getText();
            PreparedStatement statement0 = connection.prepareStatement("SELECT CustomerUN , NationalCode FROM CustomersInfo");
            ResultSet resultSet0 = statement0.executeQuery();
            while (resultSet0.next()){
                if (Objects.equals(CustomerUserName , resultSet0.getNString("CustomerUN"))){
                    isUnique = false;
                }
                if (Objects.equals(CustomerNCode , resultSet0.getString("NationalCode"))){
                    nIsUnique = false;
                }
            }
            if (!CustomersPanelController.loggedIn){
                if(HelloController.post==null){
                    if (!isUnique){
                        errorText.setText("*این نام کاربری قبلا استفاده شده است.");
                    }
                    else if (!nIsUnique){
                        errorText.setText("*کاربری با این کد ملی قبلا ثبت نام کرده است.");
                    }
                    else {
                        CreateAccReq.insert2DB(connection, currentDate, typeSlct, userText, passText, firstNameText, familyNameText, nCodeText, bDate, bPlaceText, mPhoneText, hPhoneText, addressText, codePText, fatherNameText,HelloController.userID);
                        Parent root = FXMLLoader.load(getClass().getResource("Pages/hello-view.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setTitle("Persian Express");
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        stage.centerOnScreen();
                    }
                }
                else {
                    if (isUnique && nIsUnique){
                        int uID = User.createUser(connection, userText, passText, firstNameText, familyNameText, nCodeText, fatherNameText, bDate, bPlaceText, mPhoneText, hPhoneText, addressText, codePText);
                        String accnum = GharzolH.generateAccNum(connection,typeSlctd);
                        GharzolH.createBankAcc(connection, uID, accnum, typeSlctd, nowsDate);
                        if (HelloController.post.equals("کارمند")){
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
                    else if (nIsUnique){
                        errorText.setText("نام کاربری قبلا استفاده شده است !");
                    } else {
                        errorText.setText("*کاربری با این کد ملی قبلا ثبت نام کرده است.");
                    }
                }
            }
            else {
                if (Objects.equals(typeSlctd,"انتخاب کنید")){
                    errorText.setText("*انتخاب نوع حساب الزامی است.");
                }
                else {
                    CreateAccReq.insert2DB(connection, currentDate, typeSlct, userText, passText, firstNameText, familyNameText, nCodeText, bDate, bPlaceText, mPhoneText, hPhoneText, addressText, codePText, fatherNameText,HelloController.userID);
                    Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    stage.centerOnScreen();

                }
            }
        }
        }
    }

