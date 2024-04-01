package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class CustomersChangeUNUPController {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField UserNameTXT , newUserNameTXT , PasswordTXT , newPasswordTXT ,newRepeatPasswordTXT;
    @FXML
    private Label errorTXT;
    public void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersProfile.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onRecordChangesClicked(ActionEvent event) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-0KSSE4QN;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "Nasimi" , "138374");
    public void onRecordChangesClicked(ActionEvent event) throws SQLException, IOException {
        PreparedStatement statement = connection.prepareStatement("select *from CustomersInfo where CustomerID = ? ");
        statement.setInt(1 , HelloController.userID);
        ResultSet resultSet = statement.executeQuery();
        String userName = null;
        String userPassword = null;
        while (resultSet.next()){
            userName = resultSet.getNString("CustomerUN");
            userPassword = resultSet.getNString("CustomerPassword");
        }
        if ((Objects.equals(userName, UserNameTXT.getText())) && (Objects.equals(userPassword , PasswordTXT.getText()))){
            errorTXT.setText("");
            if (newUserNameTXT.getText().isEmpty() && newPasswordTXT.getText().isEmpty() && newRepeatPasswordTXT.getText().isEmpty()){
                errorTXT.setText("نام کاربری و رمز عبور جدید خود را وارد کنید!");
            }else {
                boolean isUnique = true;
                PreparedStatement statement1 = connection.prepareStatement("select CustomerUN , CustomerPassword from CustomersInfo where CustomerID != ?");
                statement1.setInt(1 , HelloController.userID);
                ResultSet resultSet1 = statement1.executeQuery();
                while (resultSet1.next()){
                    if (Objects.equals(newUserNameTXT.getText(), userName)){
                        isUnique = false;
                        errorTXT.setText("این نام کاربری قبلا بکار رفته است!");
                    } else if (Objects.equals(newPasswordTXT.getText(), userPassword)) {
                        isUnique = false;
                        errorTXT.setText("این رمز عبور قبلا یکار رفته است!");
                    }
                }
                if (isUnique){
                    if (!Objects.equals(newPasswordTXT.getText(), newRepeatPasswordTXT.getText())) {
                        errorTXT.setText("تکرار رمز عبور جدید صحیح نمیباشد!");
                    }else if (!newUserNameTXT.getText().isEmpty() && !newPasswordTXT.getText().isEmpty()){
                        PreparedStatement statement2 = connection.prepareStatement("UPDATE CustomersInfo set CustomerUN = ? , CustomerPassword = ?  where CustomerID = ?");
                        statement2.setNString(1 , newUserNameTXT.getText());
                        statement2.setNString(2 , newPasswordTXT.getText());
                        statement2.setInt(3 , HelloController.userID);
                        int result = statement2.executeUpdate();
                        errorTXT.setText("");
                        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersProfile.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        stage.centerOnScreen();
                    } else if (!newUserNameTXT.getText().isEmpty()) {
                        PreparedStatement statement2 = connection.prepareStatement("update CustomersInfo set CustomerUN = ? where CustomerID = ?");
                        statement2.setNString(1 , newUserNameTXT.getText());
                        statement2.setInt(2 , HelloController.userID);
                        int result = statement2.executeUpdate();
                        errorTXT.setText("");
                        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersProfile.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        stage.centerOnScreen();
                    } else if (!newPasswordTXT.getText().isEmpty()) {
                        PreparedStatement statement2 = connection.prepareStatement("update CustomersInfo set CustomerPassword = ? where CustomerID = ?");
                        statement2.setNString(1 , newPasswordTXT.getText());
                        statement2.setInt(2 , HelloController.userID);
                        int result = statement2.executeUpdate();
                        errorTXT.setText("");
                        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersProfile.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        stage.centerOnScreen();
                    }
                }
            }
        }else {
            errorTXT.setText("نام کاربری و رمز عبور خود را وارد کنید!");
        }
    }
}
