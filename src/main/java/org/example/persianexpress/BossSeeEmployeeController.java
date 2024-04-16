package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class BossSeeEmployeeController {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private VBox mainVBox;
    private Connection connection;
    private PreparedStatement statement;
    private ArrayList<User> users = new ArrayList<>();
    private boolean access;
    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select *from EmployeesInfo where EmployeeID != ?");
        statement.setInt(1 , HelloController.userID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            User user = new User(resultSet.getInt("EmployeeID"));
            user.setUserName(resultSet.getNString("EmployeeUN"));
            user.setfName(resultSet.getNString("FirstName"));
            user.setLastname(resultSet.getNString("LastName"));
            user.setNationalCode(resultSet.getString("NationalCode"));
            user.setbDate(String.valueOf(resultSet.getDate("BirthDate")));
            user.setbPlace(resultSet.getNString("BirthPlace"));
            user.setPhNumber(resultSet.getString("PhoneNumber"));
            user.sethPhNumber(resultSet.getString("HomePhoneNumber"));
            user.sethAddress(resultSet.getString("HomeAddress"));
            user.setpCode(resultSet.getString("PostCode"));
            user.setAccess(resultSet.getBoolean("Access"));
            users.add(user);
        }
        int num = users.size();
        if (num > 2){
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 260*(num - 2));
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 260*(num - 2));
        }
        ShowUsers(users);
    }
    private void ShowUsers(ArrayList<User> users) throws SQLException {
        for (User user:users) {
            Label fullNamelbl = new Label("نام و نام خانوادگی : ");
            Label fullNametxt = new Label(user.getfName() + " " +user.getLastname());
            Label userNamelbl = new Label("نام کاربری : ");
            Label userNametxt = new Label(user.getUserName());
            Label userNCodelbl = new Label("کدملی : ");
            Label userNCodetxt = new Label(user.getNationalCode());
            Label postlbl = new Label("سمت :");
            Label posttxt = new Label();
            Label userBDatelbl = new Label("تاریخ تولد : ");
            Label userBDatetxt = new Label(user.getbDate());
            Label userBPlacelbl = new Label("محل تولد : ");
            Label userBPlacetxt = new Label(user.getbPlace());
            Label userPhNumlbl = new Label("شماره تماس : ");
            Label userPhNumtxt = new Label(user.getPhNumber());
            Label userHPhNumlbl = new Label("شماره تماس منزل : ");
            Label userHPhNumtxt = new Label(user.gethPhNumber());
            Label userAddresslbl = new Label("آدرس منزل : ");
            Label userAddresstxt = new Label(user.gethAddress());
            Label userPCodelbl = new Label("کد پستی منزل : ");
            Label userPCodetxt = new Label(user.getpCode());
            Button disableAcc = new Button ();
            if (user.getAccess()){
                disableAcc.setText("مسدود کردن");
                disableAcc.getStyleClass().addAll(  "button1", "darkblue" , "hover");
            }else if (!user.getAccess()){
                disableAcc.setText("فعال کردن");
                disableAcc.getStyleClass().addAll("button1" , "lightblue" , "hover");
            }
            statement = connection.prepareStatement("select EmployeePost from EmployeesInfo");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                posttxt.setText(resultSet.getNString("EmployeePost"));
            }
            fullNamelbl.getStyleClass().add("labels2");
            fullNametxt.getStyleClass().add("labels2");
            userNamelbl.getStyleClass().add("labels2");
            userNametxt.getStyleClass().add("labels2");
            userNCodelbl.getStyleClass().add("labels2");
            userNCodetxt.getStyleClass().add("labels2");
            postlbl.getStyleClass().add("labels2");
            posttxt.getStyleClass().add("labels2");
            userBDatelbl.getStyleClass().add("labels2");
            userBDatetxt.getStyleClass().add("labels2");
            userBPlacelbl.getStyleClass().add("labels2");
            userBPlacetxt.getStyleClass().add("labels2");
            userPhNumlbl.getStyleClass().add("labels2");
            userPhNumtxt.getStyleClass().add("labels2");
            userHPhNumlbl.getStyleClass().add("labels2");
            userHPhNumtxt.getStyleClass().add("labels2");
            userAddresslbl.getStyleClass().add("labels2");
            userAddresstxt.getStyleClass().add("labels2");
            userPCodelbl.getStyleClass().add("labels2");
            userPCodetxt.getStyleClass().add("labels2");
            VBox vBox1 = new VBox(5);
            VBox vBox2 = new VBox(5);
            VBox vBox3 = new VBox(5);
            VBox vBox4 = new VBox(5);
            HBox hBox1 = new HBox(2);
            vBox1.setAlignment(Pos.BASELINE_RIGHT);
            vBox2.setAlignment(Pos.BASELINE_RIGHT);
            vBox3.setAlignment(Pos.BASELINE_RIGHT);
            vBox4.setAlignment(Pos.BASELINE_RIGHT);
            vBox1.getStyleClass().add("vboxPadding");
            vBox2.getStyleClass().add("vboxPadding");
            vBox3.getStyleClass().add("vboxPadding");
            vBox4.getStyleClass().add("vboxPadding");
            vBox1.getChildren().addAll(fullNamelbl , userNCodelbl , userBDatelbl , userPhNumlbl , userAddresslbl);
            vBox2.getChildren().addAll(fullNametxt , userNCodetxt , userBDatetxt , userPhNumtxt , userAddresstxt);
            vBox3.getChildren().addAll(userNamelbl , postlbl , userBPlacelbl , userHPhNumlbl , userPCodelbl);
            vBox4.getChildren().addAll(userNametxt , posttxt , userBPlacetxt , userHPhNumtxt , userPCodetxt);
            hBox1.getChildren().add(disableAcc);
            HBox hBox2 = new HBox(4);
            hBox2.getChildren().addAll(vBox4 , vBox3 , vBox2 , vBox1);
            BorderPane borderPane1 = new BorderPane();
            borderPane1.setRight(hBox2);
            borderPane1.getStyleClass().addAll("gray");
            BorderPane borderPane2 = new BorderPane();
            hBox1.setAlignment(Pos.CENTER);
            borderPane2.setCenter(hBox1);
            borderPane2.getStyleClass().addAll("gray" , "borderPane1");
            BorderPane spacePane = new BorderPane();
            spacePane.setPrefHeight(10);

            disableAcc.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        statement = connection.prepareStatement("select Access from EmployeesInfo where EmployeeID = ?");
                        statement.setInt(1 , user.getuID());
                        ResultSet resultSet = statement.executeQuery();
                        while (resultSet.next()){
                            access = resultSet.getBoolean("Access");
                        }
                        if (access){
                            disableAcc.setText("فعال کردن");
                            disableAcc.setStyle("-fx-background-color: #023e8a");
                            statement = connection.prepareStatement("update EmployeesInfo set Access = ? where EmployeeID = ?");
                            statement.setBoolean( 1 , false);
                            statement.setInt(2 , user.getuID());
                            int success = statement.executeUpdate();
                        }else {
                            disableAcc.setText("مسدود کردن");
                            disableAcc.setStyle("-fx-background-color: #11235A");
                            statement = connection.prepareStatement("update EmployeesInfo set Access = ? where EmployeeID = ?");
                            statement.setBoolean( 1 , true);
                            statement.setInt(2 , user.getuID());
                            int success = statement.executeUpdate();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            mainVBox.getChildren().addAll(borderPane1, borderPane2 , spacePane);
        }
    }
    public void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Boss/BossPanel.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onAddEmployeeClicked(ActionEvent event) {
    }
}
