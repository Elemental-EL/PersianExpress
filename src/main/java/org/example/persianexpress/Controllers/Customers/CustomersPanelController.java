package org.example.persianexpress.Controllers.Customers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomersPanelController implements Initializable {
    @FXML
    private AnchorPane pane1 , pane2;
    @FXML
    private ChoiceBox<String> showAccountType;
    private Stage stage;
    private Scene scene;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane1.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.1) , pane2);
        translateTransition.setByX(260);
        translateTransition.play();

        pane1.setOnMouseClicked(event -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
            });

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5) , pane2);
            translateTransition1.setByX(260);
            translateTransition1.play();
        });

        String[] accountType = new String[]{"همه حساب ها" , "قرض الحسنه جاری" , "قرض الحسنه سپرده" , "سپرده کوتاه مدت" , "سپرده بلند مدت"};
        showAccountType.getItems().addAll(accountType);
        showAccountType.setValue(accountType[0]);
    }

    public void onSidebarClicked(ActionEvent event) {
        pane1.setVisible(true);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(0.15);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5) , pane2);
        translateTransition.setByX(-260);
        translateTransition.play();
    }


    public void onProfileClicked(ActionEvent event) {
    }

    public void onMessagesClicked(ActionEvent event) {
    }

    public void onExitClicked(ActionEvent event) {
    }

    public void onCreateNewAcountClicked(ActionEvent event) {
    }

    public void onDeleteAcountClicked(ActionEvent event) {
    }

    public void onMoneyTransferClicked(ActionEvent event) {
    }

    public void onRequestChequeClicked(ActionEvent event) {
    }

    public void onExportChequeClicked(ActionEvent event) {
    }

    public void onHistoryChequeClicked(ActionEvent event) {
    }

    public void OnCollectChequeClicked(ActionEvent event) {
    }

    public void onRequestBankCardClicked(ActionEvent event) {
    }

    public void onRequestsHistoryClicked(ActionEvent event) {
    }

    public void onHistoryClicked(ActionEvent event) {
    }

    public void onAddSuggestionClicked(ActionEvent event) {
    }

    public void onCashWithdrawalClicked(ActionEvent event) {
    }

    public void onDepositClicked(ActionEvent event) {
    }

    public void onLoanRequestClicked(ActionEvent event) {
    }

    public void onPayLoanClicked(ActionEvent event) {
    }

    public void onLoanHistoryClicked(ActionEvent event) {
    }

    public void onPayBillClicked(ActionEvent event) {
    }

    public void onChargingServiceClicked(ActionEvent event) {
    }
}
