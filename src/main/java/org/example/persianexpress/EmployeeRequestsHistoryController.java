package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.Request;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeRequestsHistoryController {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private VBox mainVBox;
    private Connection connection;
    private PreparedStatement statement;
    private ArrayList<Request> requests = new ArrayList<>();

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select *from RequestsHistory");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            Request request = new Request();
            request.setReqID(resultSet.getInt("RequestID"));
            request.setReqerID(resultSet.getInt("CustomerID"));
            request.setReqDate(resultSet.getDate("RequestDate"));
            request.setReqStat(resultSet.getBoolean("RequestStatus"));
            requests.add(request);
        }
        mainPane.setPrefHeight(500);
        mainVBox.setPrefHeight(500);
        int number = requests.size();
        if (number > 2){
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 180*(number - 2));
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 180*(number - 2));
        }
        ShowRequests(requests);
    }
    private void ShowRequests(ArrayList<Request> requests) throws SQLException {
        for (Request request:requests) {
            statement = connection.prepareStatement("select FirstName , LastName from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , request.getReqerID());
            ResultSet resultSet = statement.executeQuery();
            Label reqTypelbl = new Label("نوع درخواست :");
            Label reqTypetxt = new Label();
            Label reqerNamelbl = new Label("نام و نام خانوادگی :");
            Label reqerNametxt = new Label();
            Label reqDatelbl = new Label("تاریخ ثبت درخواست");
            Label reqDatetxt = new Label(String.valueOf(request.getReqDate()));
            Label reqStatuslbl = new Label("وضعیت درخواست :");
            Label reqStatustxt = new Label();
            if (request.getReqID() > 100000 && request.getReqID()<200000){
                reqTypetxt.setText("ایجاد حساب جدید");
            } else if (request.getReqID() > 200000 && request.getReqID() < 300000) {
                reqTypetxt.setText("بستن حساب");
            } else if (request.getReqID() > 300000 && request.getReqID() < 400000) {
                reqTypetxt.setText("درخواست کارت بانکی");
            } else if (request.getReqID() > 400000 && request.getReqID() < 500000) {
                reqTypetxt.setText("درخواست وام");
            } else if (request.getReqID() > 500000 && request.getReqID() < 600000) {
                reqTypetxt.setText("درخواست دسته چک");
            } else if (request.getReqID() > 600000 && request.getReqID() < 700000) {
                reqTypetxt.setText("وصول چک");
            }
            while (resultSet.next()){
                reqerNametxt.setText(resultSet.getNString("FirstName") + " " + resultSet.getNString("LastName"));
            }
            if (request.isReqStat()){
                reqStatustxt.setText("تایید شده");
            } else if (!request.isReqStat()) {
                reqStatustxt.setText("رد شده");
            }
            reqTypelbl.getStyleClass().add("labels2");
            reqTypetxt.getStyleClass().add("labels2");
            reqerNamelbl.getStyleClass().add("labels2");
            reqerNametxt.getStyleClass().add("labels2");
            reqDatelbl.getStyleClass().add("labels2");
            reqDatetxt.getStyleClass().add("labels2");
            reqStatuslbl.getStyleClass().add("labels2");
            reqStatustxt.getStyleClass().add("labels2");
            VBox vBox1 = new VBox(4);
            VBox vBox2 = new VBox(4);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox1.getChildren().addAll(reqTypelbl , reqerNamelbl , reqDatelbl , reqStatuslbl);
            vBox2.getChildren().addAll(reqTypetxt , reqerNametxt , reqDatetxt , reqStatustxt);
            BorderPane borderPane = new BorderPane();
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            borderPane.getStyleClass().addAll("CPBorderPane1" , "gray");
            BorderPane spacePane = new BorderPane();
            spacePane.setStyle("-fx-pref-height: 10");
            mainVBox.getChildren().addAll(borderPane , spacePane);
        }
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
