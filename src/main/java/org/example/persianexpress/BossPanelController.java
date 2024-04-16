package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class BossPanelController {
    private Stage stage;
    private Scene scene;
    @FXML
    private Label empName,empRole;
    private Connection connection;
    private PreparedStatement statement;
    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select FirstName , LastName , EmployeePost from EmployeesInfo where EmployeeID = ?");
        statement.setInt(1 , HelloController.userID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            empName.setText(resultSet.getNString("FirstName") + " " + resultSet.getNString("LastName"));
            empRole.setText(resultSet.getNString("EmployeePost"));
        }
    }
    public void onBackClicked(MouseEvent event) {
    }

    public void onLogOutClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/hello-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
        HelloController.userID = 100;
    }

    public void onSeeUsersClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeUsers.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onSeeAccsClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeAccounts.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onSeeReqsClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onReqsHistoryClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/RequestsHistory.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onSendMessageClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SendMessage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onInboxClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeMessages.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onSuggestionsClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeSuggestions.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onMessagesHistoryClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/MessagesHistory.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onSeeEmployeeClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Boss/SeeEmployee.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
}
