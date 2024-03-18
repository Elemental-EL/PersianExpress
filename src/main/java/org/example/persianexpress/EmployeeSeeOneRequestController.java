package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.CreateAccReq;
import org.example.persianexpress.Objects.GharzolH;
import org.example.persianexpress.Objects.Request;
import org.example.persianexpress.Objects.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class EmployeeSeeOneRequestController {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private VBox mainVBox;
    private Connection connection;
    private ResultSet resultSet;
    private LocalDate nowsDate = LocalDate.now();

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-IQ6LNQ5;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "PEDB" , "pedb1234");
        resultSet = Request.getReq(connection,EmployeeSeeRequestsController.REQID);
    }

    public void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Persian Express");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onRejectClicked(ActionEvent event) throws SQLException, IOException {
        Request.passToHistory(connection, resultSet, false);
        Request.deleteFromCreateAccountREQ(connection, resultSet);
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Persian Express");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onConfirmClicked(ActionEvent event) throws SQLException, IOException {
        String reqType = Request.getReqType(EmployeeSeeRequestsController.REQID);
        if (reqType.equals("CreateAccountREQ")){
            while (resultSet.next()) {
                if (resultSet.getInt("CustomerID") == 100) {
                    int uID = User.createUser(connection, resultSet);
                    GharzolH.createBankAcc(connection, uID, GharzolH.generateAccNum(connection, resultSet.getNString("AccountType")), resultSet.getNString("AccountType"), nowsDate);
                    Request.passToHistory(connection, resultSet, true);
                    Request.deleteFromCreateAccountREQ(connection, resultSet);
                    Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setTitle("Persian Express");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    stage.centerOnScreen();
                } else {
                    GharzolH.createBankAcc(connection, resultSet.getInt("CustomerID"), GharzolH.generateAccNum(connection, resultSet.getNString("AccountType")), resultSet.getNString("AccountType"), nowsDate);
                    Request.passToHistory(connection, resultSet, true);
                    Request.deleteFromCreateAccountREQ(connection, resultSet);
                    Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/SeeRequests.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setTitle("Persian Express");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    stage.centerOnScreen();
                }
            }
        }
    }




}
