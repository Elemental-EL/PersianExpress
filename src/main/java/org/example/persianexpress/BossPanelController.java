package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class BossPanelController {
    private Stage stage;
    private Scene scene;
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

    public void onSeeUsersClicked(ActionEvent event) {
    }

    public void onSeeAccsClicked(ActionEvent event) {
    }

    public void onSeeReqsClicked(ActionEvent event) {
    }

    public void onReqsHistoryClicked(ActionEvent event) {
    }

    public void onSendMessageClicked(ActionEvent event) {
    }

    public void onInboxClicked(ActionEvent event) {
    }

    public void onSuggestionsClicked(ActionEvent event) {
    }

    public void onMessagesHistoryClicked(ActionEvent event) {
    }

    public void onSeeEmployeeClicked(ActionEvent event) {
    }
}
