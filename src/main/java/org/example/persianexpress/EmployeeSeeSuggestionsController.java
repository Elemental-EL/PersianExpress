package org.example.persianexpress;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import org.example.persianexpress.Objects.Suggestion;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeSeeSuggestionsController {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private VBox mainVBox;

    private Connection connection;
    private PreparedStatement statement;
    private ArrayList<Suggestion> suggestions = new ArrayList<>();

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select *from Suggestions");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            Suggestion suggestion = new Suggestion(resultSet.getInt("SuggestionID"));
            suggestion.setSuggestionDate(resultSet.getDate("SuggestionDate"));
            suggestion.setSuggestionContext(resultSet.getNString("Suggestion"));
            suggestions.add(suggestion);
        }
        ShowSuggestions(suggestions);
    }
    private void ShowSuggestions(ArrayList<Suggestion> suggestions){
        for (Suggestion suggestion:suggestions) {
            Label sendDatelbl = new Label("تاریخ ارسال :");
            Label sendDatetxt = new Label(String.valueOf(suggestion.getSuggestionDate()));
            TextArea SuggTXT = new TextArea(suggestion.getSuggestionContext());
            sendDatelbl.getStyleClass().add("labels");
            sendDatetxt.getStyleClass().add("labels");
            HBox hBox1 = new HBox(2);
            HBox hBox2 = new HBox(1);
            SuggTXT.getStyleClass().add("labels");
            SuggTXT.setStyle("-fx-pref-width: 730; -fx-pref-height: 150");
            SuggTXT.setEditable(false);
            hBox1.getChildren().addAll(sendDatetxt , sendDatelbl);
            hBox2.getChildren().add(SuggTXT);
            BorderPane borderPane1 = new BorderPane();
            BorderPane borderPane2 = new BorderPane();
            borderPane1.setRight(hBox1);
            borderPane2.setCenter(hBox2);
            VBox vBox = new VBox(2);
            vBox.getChildren().addAll(borderPane1 , borderPane2);
            BorderPane borderPane4 = new BorderPane();
            borderPane4.setRight(vBox);
            borderPane4.getStyleClass().addAll("gray" , "CPBorderPane1");
            BorderPane spacePane = new BorderPane();
            spacePane.setStyle("-fx-pref-height: 10");
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
}
