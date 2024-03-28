package org.example.persianexpress;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.Message;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeMessagesHistoryController {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private VBox mainVBox;
    private Connection connection;
    private PreparedStatement statement;
    private ArrayList<Message> messages = new ArrayList<>();

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select *from Messages where senderID = ?");
        statement.setInt(1 , HelloController.userID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            Message message = new Message(resultSet.getInt("MessageID") , resultSet.getInt("SenderID") , resultSet.getInt("CustomerID"));
            message.setMessageContext(resultSet.getNString("Message"));
            message.setMessageDate(resultSet.getDate("PostageDate"));
            message.setMessageStat(resultSet.getBoolean("MessageStatus"));
            messages.add(message);
        }
        int num = messages.size();
        if (num > 2 ){
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 250*(num - 2));
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 250*(num - 2));
        }
        EployeeSendMess(messages);
    }
    public void EployeeSendMess(ArrayList<Message> messages) throws SQLException {
        for (Message message:messages) {
            statement = connection.prepareStatement("select FirstName , LastName , CustomerUN from CustomersInfo where CustomerId = ?");
            statement.setInt(1 , message.getCustomerID());
            ResultSet resultSet = statement.executeQuery();
            String fullName = null;
            String userName = null;
            while (resultSet.next()){
                fullName = resultSet.getNString("FirstName") + " " + resultSet.getString("LastName");
                userName = resultSet.getNString("CustomerUN");
            }
            Label fullNamelbl = new Label("نام و نام خانوادگی :");
            Label fullNametxt = new Label(fullName);
            Label userNamelbl = new Label("نام کابری :");
            Label userNametxt = new Label(userName);
            TextArea messageTXT = new TextArea(message.getMessageContext());
            Label sendDatelbl = new Label("تاریخ ارسال :");
            Label sendDatetxt = new Label(String.valueOf(message.getMessageDate()));
            Label messStatuslbl = new Label("وضعیت پیام :");
            Label messStatustxt = new Label();
            if (message.isMessageStat()){
                messStatustxt.setText("خوانده شده");
            }else {
                messStatustxt.setText("خوانده نشده");
            }
            fullNamelbl.getStyleClass().add("labels");
            fullNametxt.getStyleClass().add("labels");
            userNamelbl.getStyleClass().add("labels");
            userNametxt.getStyleClass().add("labels");
            messageTXT.getStyleClass().add("labels");
            messageTXT.setStyle("-fx-pref-width: 730; -fx-pref-height: 150");
            messageTXT.setEditable(false);
            sendDatelbl.getStyleClass().add("labels");
            sendDatetxt.getStyleClass().add("labels");
            messStatuslbl.getStyleClass().add("labels");
            messStatustxt.getStyleClass().add("labels");
            HBox hBox1 = new HBox(2);
            HBox hBox2 = new HBox(2);
            HBox hBox3 = new HBox(2);
            HBox hBox4 = new HBox(2);
            hBox1.setAlignment(Pos.CENTER_RIGHT);
            hBox2.setAlignment(Pos.CENTER_RIGHT);
            hBox3.setAlignment(Pos.CENTER_RIGHT);
            hBox4.setAlignment(Pos.CENTER_RIGHT);
            hBox1.getChildren().addAll(fullNametxt , fullNamelbl);
            hBox2.getChildren().addAll(userNametxt , userNamelbl);
            hBox3.getChildren().addAll(sendDatetxt , sendDatelbl);
            hBox4.getChildren().addAll(messStatustxt , messStatuslbl);
            BorderPane borderPane1 = new BorderPane();
            BorderPane borderPane2 = new BorderPane();
            BorderPane borderPane3 = new BorderPane();
            BorderPane borderPane4 = new BorderPane();
            BorderPane spacePane = new BorderPane();
            VBox vBox = new VBox(3);
            borderPane1.setRight(hBox1);
            borderPane1.setLeft(hBox2);
            borderPane2.setCenter(messageTXT);
            borderPane3.setRight(hBox3);
            borderPane3.setLeft(hBox4);
            borderPane4.getStyleClass().addAll("gray" , "CPBorderPane1");
            spacePane.setPrefHeight(10);
            vBox.getChildren().addAll(borderPane1 , borderPane2 , borderPane3);
            borderPane4.setRight(vBox);
            mainVBox.getChildren().addAll(borderPane4 , spacePane);
        }
    }
    public void onBackClicked(MouseEvent event) throws IOException {
        if (HelloController.userID == 101){
            Parent root = FXMLLoader.load(getClass().getResource("Pages/Boss/BossPanel.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Persian Express");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }else {
            Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/EmployeePanel.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Persian Express");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

        stage.centerOnScreen();
    }
}
