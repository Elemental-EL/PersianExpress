package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
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

public class EmployeeSeeMessagesController {
    private Stage stage;
    private Scene scene;
    @FXML
    private ImageView backBtn;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private VBox mainVBox;
    private Connection connection;
    private PreparedStatement statement;
    private ArrayList<Message> messages = new ArrayList<>();
    private String post;

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select *from Messages where CustomerID = ?");
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
        if (num > 2){
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 250*(messages.size() - 2));
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 250*(messages.size() - 2));
        }
        ShowUnread(messages);
        ShowReaded(messages);
    }

    private void ShowUnread(ArrayList<Message> messages) throws SQLException {
        for (Message message:messages) {
            statement = connection.prepareStatement("select EmployeePost from EmployeesInfo where EmployeeID = ?");
            statement.setInt(1 , message.getSenderUID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                post = resultSet.getNString("EmployeePost");
            }
            if (!message.isMessageStat()){
                Label senderlbl = new Label("فرستنده : ");
                Label sendertxt = new Label(post);
                TextArea messagetxt = new TextArea(message.getMessageContext());
                Label sendDatelbl = new Label("تاریخ ارسال :");
                Label sendDatetxt = new Label(String.valueOf(message.getMessageDate()));
                Label messageStatuslbl = new Label("وضعیت : ");
                Label messageStatustxt = new Label("جدید");
                senderlbl.getStyleClass().add("labels");
                sendertxt.getStyleClass().add("labels");
                messagetxt.getStyleClass().add("labels");
                messagetxt.setStyle("-fx-pref-width: 730; -fx-pref-height: 150");
                messagetxt.setEditable(false);
                sendDatelbl.getStyleClass().add("labels");
                sendDatetxt.getStyleClass().add("labels");
                messageStatuslbl.getStyleClass().add("labels");
                messageStatustxt.getStyleClass().add("labels");
                HBox hBox1 = new HBox(2);
                HBox hBox2 = new HBox(2);
                HBox hBox3 = new HBox(2);
                HBox hBox4 = new HBox(2);
                hBox1.getChildren().addAll(sendertxt , senderlbl);
                hBox2.getChildren().addAll(sendDatetxt , sendDatelbl);
                hBox3.getChildren().addAll(messageStatustxt , messageStatuslbl);
                hBox4.getChildren().add(messagetxt);
                BorderPane borderPane1 = new BorderPane();
                BorderPane borderPane2 = new BorderPane();
                BorderPane borderPane3 = new BorderPane();
                borderPane1.setRight(hBox1);
                borderPane2.setRight(hBox4);
                borderPane3.setRight(hBox2);
                borderPane3.setLeft(hBox3);
                VBox vBox = new VBox(3);
                vBox.getChildren().addAll(borderPane1, borderPane2  , borderPane3);
                BorderPane borderPane4 = new BorderPane();
                borderPane4.setRight(vBox);
                borderPane4.getStyleClass().addAll("gray" , "CPBorderPane1");
                BorderPane spacePane = new BorderPane();
                spacePane.setStyle("-fx-pref-height: 10");
                mainVBox.getChildren().addAll(borderPane4 , spacePane);
                statement = connection.prepareStatement("update Messages set MessageStatus = ? where MessageID = ?");
                statement.setBoolean(1 , true);
                statement.setInt(2 , message.getMessageID());
                int success = statement.executeUpdate();
            }
        }
    }
    private void ShowReaded(ArrayList<Message> messages) throws SQLException {
        for (Message message:messages) {
            statement = connection.prepareStatement("select EmployeePost from EmployeesInfo where EmployeeID = ?");
            statement.setInt(1 , message.getSenderUID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                post = resultSet.getNString("EmployeePost");
            }
            if (message.isMessageStat()){
                Label senderlbl = new Label("فرستنده : ");
                Label sendertxt = new Label(post);
                TextArea messagetxt = new TextArea(message.getMessageContext());
                Label sendDatelbl = new Label("تاریخ ارسال :");
                Label sendDatetxt = new Label(String.valueOf(message.getMessageDate()));
                Label messageStatuslbl = new Label("وضعیت : ");
                Label messageStatustxt = new Label("خوانده شده");
                senderlbl.getStyleClass().add("labels");
                sendertxt.getStyleClass().add("labels");
                messagetxt.getStyleClass().add("labels");
                messagetxt.setStyle("-fx-pref-width: 730; -fx-pref-height: 150");
                messagetxt.setEditable(false);
                sendDatelbl.getStyleClass().add("labels");
                sendDatetxt.getStyleClass().add("labels");
                messageStatuslbl.getStyleClass().add("labels");
                messageStatustxt.getStyleClass().add("labels");
                HBox hBox1 = new HBox(2);
                HBox hBox2 = new HBox(2);
                HBox hBox3 = new HBox(2);
                HBox hBox4 = new HBox(2);
                hBox1.getChildren().addAll(sendertxt , senderlbl);
                hBox2.getChildren().addAll(sendDatetxt , sendDatelbl);
                hBox3.getChildren().addAll(messageStatustxt , messageStatuslbl);
                hBox4.getChildren().add(messagetxt);
                BorderPane borderPane1 = new BorderPane();
                BorderPane borderPane2 = new BorderPane();
                BorderPane borderPane3 = new BorderPane();
                borderPane1.setRight(hBox1);
                borderPane2.setRight(hBox4);
                borderPane3.setRight(hBox2);
                borderPane3.setLeft(hBox3);
                VBox vBox = new VBox(3);
                vBox.getChildren().addAll(borderPane1, borderPane2  , borderPane3);
                BorderPane borderPane4 = new BorderPane();
                borderPane4.setRight(vBox);
                borderPane4.getStyleClass().addAll("gray" , "CPBorderPane1");
                BorderPane spacePane = new BorderPane();
                spacePane.setStyle("-fx-pref-height: 10");
                mainVBox.getChildren().addAll(borderPane4 , spacePane);
            }
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
            stage.centerOnScreen();
        }else {
            Parent root = FXMLLoader.load(getClass().getResource("Pages/Employee/EmployeePanel.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Persian Express");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();        }

    }
}
