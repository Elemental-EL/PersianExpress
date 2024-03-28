package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class CustomersSuggestionController {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextArea suggestionTXT;
    @FXML
    private Label errorText;
    private Connection connection;
    private PreparedStatement statement;
    private java.sql.Date currentDate = Date.valueOf(LocalDate.now());


    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
    }
    public void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onSendClicked(ActionEvent event) throws SQLException {
        statement = connection.prepareStatement("Insert Into Suggestions(SuggestionDate , Suggestion) Values (? , ?)");
        statement.setDate(1 , currentDate);
        statement.setNString(2 , suggestionTXT.getText());
        int res = statement.executeUpdate();
        if (res == 0){
            errorText.setText("ارسال ممکن نمیباشد !!");
        } else if (res == 1 ) {
            errorText.setText("با موفقیت ارسال شد .");
            suggestionTXT.setText("");
        }
    }
}
