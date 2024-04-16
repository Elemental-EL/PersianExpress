package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.Person;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeSendMessageController {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextArea messageTXT;
    @FXML
    private ChoiceBox<String> ChoiceUser;
    @FXML
    private Label errorTXT;
    private java.sql.Date currentDate = Date.valueOf(LocalDate.now());
    private Connection connection;
    private PreparedStatement statement;
    ArrayList<Person> persons = new ArrayList<>();
    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select CustomerID , CustomerUN from CustomersInfo");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            Person person = new Person();
            person.setuID(resultSet.getInt("CustomerID"));
            person.setUserName(resultSet.getNString("CustomerUN"));
            persons.add(person);
        }
        statement = connection.prepareStatement("select EmployeeID , EmployeeUN , EmployeePost from EmployeesInfo where EmployeeID != ? ");
        statement.setInt(1 , HelloController.userID);
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            Person person = new Person();
            person.setuID(resultSet.getInt("EmployeeID"));
            person.setUserName(resultSet.getNString("EmployeeUN")+ " " + "(" + resultSet.getNString("EmployeePost") + ")");
            persons.add(person);
        }
        ChoiceUser.getItems().add("همه کاربران");
        ChoiceUser.setValue("همه کاربران");
        for (Person person: persons) {
            ChoiceUser.getItems().add(person.getUserName());
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
            stage.centerOnScreen();
        }

    }

    public void onSendClicked(ActionEvent event) throws SQLException {
        int i = ChoiceUser.getSelectionModel().getSelectedIndex();
        if (i == 0){
            if (messageTXT.getText().isEmpty()){
                errorTXT.setText("لطفا پیام خود را وارد کنید !");
            }else {
                for (Person person:persons) {
                    statement = connection.prepareStatement("INSERT INTO Messages(SenderID , CustomerID , Message , PostageDate , MessageStatus) values (? , ? , ? , ? , ?)");
                    statement.setInt(1 , HelloController.userID);
                    statement.setInt(2 , person.getuID());
                    statement.setNString(3 , messageTXT.getText());
                    statement.setDate(4 , currentDate);
                    statement.setBoolean(5 , false);
                    int success = statement.executeUpdate();
                }
            }
        } else if (i >= 1 && i <= persons.size()){
            if (messageTXT.getText().isEmpty()){
                errorTXT.setText("لطفا پیام خود را وارد کنید !");
            }else {
                statement = connection.prepareStatement("Insert Into Messages(SenderID , CustomerID , Message , PostageDate , MessageStatus) values (? , ? , ? , ? , ?)");
                statement.setInt(1 , HelloController.userID);
                statement.setInt(2 , persons.get(i-1).getuID());
                statement.setNString(3 , messageTXT.getText());
                statement.setDate(4 , currentDate);
                statement.setBoolean(5 , false);
                int success = statement.executeUpdate();
                if (success == 0){
                    errorTXT.setText("ارسال پیام ممکن نمیباشد !!");
                }if (success == 1){
                    errorTXT.setText("پیام با موفقیت ارسال شد.");
                }
            }

        }else {
            errorTXT.setText("کاربر انتخاب شده معتبر نمیباشد !!");
        }

    }
}
