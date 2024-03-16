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

import java.sql.*;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Objects;

public class HelloController {
    private Stage stage;
    private Scene scene;
    @FXML
    private ChoiceBox<String> roleCB;
    @FXML
    private TextField userText;
    @FXML
    private TextField passwordText;
    @FXML
    private Label errorTXT;
    private String CustomerUserName;
    private String CustomerPassword;
    private String password;
    public static String post = null;
    private Boolean acc;
    public static int userID = 100;

    public void initialize() throws SQLException {
        String[] role = new String[]{"انتخاب کنید" , "کاربر" , "کارمند" ,"مدیر"};
        roleCB.getItems().addAll(role);
        roleCB.setValue(role[0]);
    }

    public void onloginBtnClicked(ActionEvent event) throws IOException, SQLException {
        String role = roleCB.getSelectionModel().getSelectedItem();
        CustomerUserName = userText.getText();
        CustomerPassword = passwordText.getText();
        if (Objects.equals(role, "انتخاب کنید")){
            errorTXT.setText("لطفا نقش خود را انتخاب کنید!");
        }else if (Objects.equals(role, "کاربر")){
            if (CustomerUserName.isEmpty() || CustomerPassword.isEmpty()){
                errorTXT.setText("نام کاربری و رمز عبور خود را وارد کنید!");
            }else {
                Connection connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
                PreparedStatement statement = connection.prepareStatement("select CustomerID , CustomerPassword , Access from  CustomersInfo where CustomerUN = ? ");
                statement.setNString(1 , CustomerUserName);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    password = resultSet.getNString("CustomerPassword");
                    acc = resultSet.getBoolean("Access");
                    userID = resultSet.getInt("CustomerID");
                }
                if (Objects.equals(CustomerPassword, password) && acc){
                    Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setTitle("Persian Express");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    stage.centerOnScreen();
                }else if (!Objects.equals(CustomerPassword, password)){
                    errorTXT.setText("نام کاربری یا رمز عبور اشتباه است");
                } else if (!acc) {
                    errorTXT.setText("حساب کاربری شما مسدود است");
                }
                post = null;
            }
        } else if (Objects.equals(role, "مدیر") || Objects.equals(role, "کارمند")) {
            if (CustomerUserName.isEmpty() || CustomerPassword.isEmpty()){
                errorTXT.setText("نام کاربری و رمز عبور خود را وارد کنید!");
            }else {
                Connection connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
                PreparedStatement statement = connection.prepareStatement("select EmployeeID , EmployeePassword , EmployeePost , Access from  EmployeesInfo where EmployeeUN = ? ");
                statement.setNString(1 , CustomerUserName);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    password = resultSet.getNString("EmployeePassword");
                    post = resultSet.getNString("EmployeePost");
                    acc = resultSet.getBoolean("Access");
                    userID = resultSet.getInt("EmployeeID");
                }
                if (Objects.equals(CustomerPassword, password) && acc){
                    if (role.equals("کارمند") && post.equals("کارمند")){
                        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/EmployeePanel.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setTitle("Persian Express");
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        stage.centerOnScreen();
                    } else if (role.equals("مدیر") && post.equals("مدیر")) {
                        Parent root = FXMLLoader.load(getClass().getResource("Pages/Boss/BossPanel.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setTitle("Persian Express");
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        stage.centerOnScreen();
                    } else if (!post.equals(role)) {
                        errorTXT.setText("نقش انتخاب شده اشتباه است");
                    }

                }else if (!Objects.equals(CustomerPassword, password)){
                    errorTXT.setText("نام کاربری یا رمز عبور اشتباه است");
                }else if (!acc){
                    errorTXT.setText("حساب کاربری شما مسدود است");
                }
            }
        }
    }

    public void onSigninTextClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CreateAccount.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

}


//    Hamed Sql server Connection
//    "jdbc:sqlserver://DESKTOP-98DDBT0\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt"

//    Elyar sql server Connection
//

//    Helia sql server Connection
//