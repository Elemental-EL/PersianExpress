package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;

import java.io.IOException;
import java.net.ConnectException;

public class HelloController {
    private Stage stage;
    private Scene scene;
    @FXML
    private ChoiceBox<String> roleCB;

    public void initialize() throws SQLException {
        String[] role = new String[]{"انتخاب کنید" , "کاربر" , "کارمند" ,"مدیر"};
        roleCB.getItems().addAll(role);
        roleCB.setValue(role[0]);
    }

    public void onloginBtnClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/EmployeePanel.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Persian Express");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
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