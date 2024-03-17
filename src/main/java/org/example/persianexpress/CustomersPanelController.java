package org.example.persianexpress;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.persianexpress.Objects.GharzolH;
import org.example.persianexpress.Objects.Sepordeh;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomersPanelController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane pane1 , pane2;
    @FXML
    private ChoiceBox<String> showAccountType;
    @FXML
    private AnchorPane showAcountsPane;
    @FXML
    private VBox showAcountsVBox;
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

        ArrayList<GharzolH> gharzolHSAcc = new ArrayList<>();
        ArrayList<Sepordeh> sepordehAcc = new ArrayList<>();
        int i = 0;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
            PreparedStatement statement = connection.prepareStatement("select *from BankAccounts");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String accType = resultSet.getNString("AccountType");
                if (Objects.equals(accType, "قرض الحسنه جاری") || Objects.equals(accType, "قرض الحسنه سپرده")){
                    GharzolH account1 = new GharzolH();
                    account1.setAccNumber(resultSet.getNString("AccountNumber"));
                    account1.setAccType(resultSet.getNString("AccountType"));
                    account1.setAccBalance(resultSet.getLong("AccountStock"));
                    account1.setAccAccess(resultSet.getBoolean("AccountAccess"));
                    gharzolHSAcc.add(account1);
                    i++;
                } else if (Objects.equals(accType, "سپرده کوتاه مدت") || Objects.equals(accType, "سپرده بلند مدت")) {
                    Sepordeh account2 = new Sepordeh();
                    account2.setAccNumber(resultSet.getNString("AccountNumber"));
                    account2.setAccType(resultSet.getNString("AccountType"));
                    account2.setAccBalance(resultSet.getLong("AccountStock"));
                    account2.setAccInterest(resultSet.getInt("AccountProfit"));
                    account2.setAccTerminationDate(resultSet.getDate("AccountTerm"));
                    account2.setAccAccess(resultSet.getBoolean("AccountAccess"));
                    sepordehAcc.add(account2);
                    i++;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error");
        }

        ShowAllAccounts(gharzolHSAcc , sepordehAcc , i);
        int finalI = i;
        showAccountType.setOnAction(event -> {
            if (Objects.equals(showAccountType.getValue(), "قرض الحسنه جاری")) {
                showAcountsVBox.getChildren().clear();
                ShowGHarzeJari(gharzolHSAcc);
            }else if (Objects.equals(showAccountType.getValue(), "قرض الحسنه سپرده")) {
                showAcountsVBox.getChildren().clear();
                ShowGharzeSepordeh(gharzolHSAcc);
            }else if (Objects.equals(showAccountType.getValue(), "سپرده کوتاه مدت")) {
                showAcountsVBox.getChildren().clear();
                ShowSepordehKotah(sepordehAcc);
            }else if (Objects.equals(showAccountType.getValue(), "قرض بلند مدت")) {
                showAcountsVBox.getChildren().clear();
                ShowSepordehBoland(sepordehAcc);
            } else if (Objects.equals(showAccountType.getValue(), "همه حساب ها")) {
                showAcountsVBox.getChildren().clear();
                ShowAllAccounts(gharzolHSAcc , sepordehAcc , finalI);
            }
        });
    }


    private void ShowAllAccounts(ArrayList<GharzolH> gharzolHS , ArrayList<Sepordeh> sepordehs  , int i){
        for (GharzolH gharz:gharzolHS) {
            Label accTypelbl = new Label("نوع حساب : ");
            Label accNumlbl = new Label("شماره حساب : ");
            Label accStocklbl = new Label("موجودی :(ریال) ");
            Label accTypetxt = new Label(gharz.getAccType());
            Label accNumtxt = new Label(gharz.getAccNumber());
            Label accStocktxt = new Label(String.valueOf(gharz.getAccBalance()));
            accTypelbl.getStyleClass().add("labels");
            accNumlbl.getStyleClass().add("labels");
            accStocklbl.getStyleClass().add("labels");
            accTypetxt.getStyleClass().add("labels");
            accNumtxt.getStyleClass().add("labels");
            accStocktxt.getStyleClass().add("labels");
            VBox vBox1 = new VBox(3);
            VBox vBox2 = new VBox(3);
            vBox1.getChildren().addAll(accTypelbl , accNumlbl , accStocklbl);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox2.getChildren().addAll(accTypetxt , accNumtxt , accStocktxt);
            BorderPane borderPane = new BorderPane();
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
            BorderPane spacePane = new BorderPane();
            spacePane.setStyle("-fx-pref-height: 20");
            showAcountsVBox.getChildren().addAll(borderPane , spacePane);
        }
        for (Sepordeh sepordeh: sepordehs) {
            Label accTypelbl = new Label("نوع حساب : ");
            Label accNumlbl = new Label("شماره حساب : ");
            Label accStocklbl = new Label("موجودی :(ریال) ");
            Label accInterestlbl = new Label("نرخ سود سالانه : ");
            Label accTermlbl = new Label("تاریخ سررسید سپرده : ");
            Label accTypetxt = new Label(sepordeh.getAccType());
            Label accNumtxt = new Label(sepordeh.getAccNumber());
            Label accStocktxt = new Label(String.valueOf(sepordeh.getAccBalance()));
            Label accInteresttxt = new Label(String.valueOf(sepordeh.getAccInterest()) + "%");
            Label accTermtxt = new Label(String.valueOf(sepordeh.getAccTerminationDate()));
            accTypelbl.getStyleClass().add("labels");
            accNumlbl.getStyleClass().add("labels");
            accStocklbl.getStyleClass().add("labels");
            accInterestlbl.getStyleClass().add("labels");
            accTermlbl.getStyleClass().add("labels");
            accTypetxt.getStyleClass().add("labels");
            accNumtxt.getStyleClass().add("labels");
            accStocktxt.getStyleClass().add("labels");
            accInteresttxt.getStyleClass().add("labels");
            accTermtxt.getStyleClass().add("labels");
            VBox vBox1 = new VBox(5);
            VBox vBox2 = new VBox(5);
            vBox1.getChildren().addAll(accTypelbl , accNumlbl , accStocklbl , accInterestlbl , accTermlbl);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox2.getChildren().addAll(accTypetxt , accNumtxt , accStocktxt , accInteresttxt , accTermtxt);
            BorderPane borderPane = new BorderPane();
            borderPane.setRight(vBox1);
            borderPane.setLeft(vBox2);
            borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
            BorderPane spacePane = new BorderPane();
            spacePane.setStyle("-fx-pref-height: 20");
            showAcountsVBox.getChildren().addAll(borderPane , spacePane);
        }
        showAcountsPane.setPrefHeight(450);
        showAcountsVBox.setPrefHeight(450);
        if (i >= 3){
            showAcountsPane.setPrefHeight(showAcountsPane.getPrefHeight() + 150*(i-2));
            showAcountsVBox.setPrefHeight(showAcountsVBox.getPrefHeight() + 150*(i-2));
        }
    }
    private void ShowGHarzeJari(ArrayList<GharzolH> gharzolHS){
        int i = 0;
        for (GharzolH gharz:gharzolHS) {
            if (Objects.equals(gharz.getAccType(), "قرض الحسنه جاری")){
                Label accTypelbl = new Label("نوع حساب : ");
                Label accNumlbl = new Label("شماره حساب : ");
                Label accStocklbl = new Label("موجودی :(ریال) ");
                Label accTypetxt = new Label(gharz.getAccType());
                Label accNumtxt = new Label(gharz.getAccNumber());
                Label accStocktxt = new Label(String.valueOf(gharz.getAccBalance()));
                accTypelbl.getStyleClass().add("labels");
                accNumlbl.getStyleClass().add("labels");
                accStocklbl.getStyleClass().add("labels");
                accTypetxt.getStyleClass().add("labels");
                accNumtxt.getStyleClass().add("labels");
                accStocktxt.getStyleClass().add("labels");
                VBox vBox1 = new VBox(3);
                VBox vBox2 = new VBox(3);
                vBox1.getChildren().addAll(accTypelbl , accNumlbl , accStocklbl);
                vBox1.setAlignment(Pos.CENTER_RIGHT);
                vBox2.getChildren().addAll(accTypetxt , accNumtxt , accStocktxt);
                BorderPane borderPane = new BorderPane();
                borderPane.setRight(vBox1);
                borderPane.setLeft(vBox2);
                borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
                BorderPane spacePane = new BorderPane();
                spacePane.setStyle("-fx-pref-height: 20");
                showAcountsVBox.getChildren().addAll(borderPane , spacePane);
                i++;
            }
        }
        showAcountsPane.setPrefHeight(450);
        showAcountsVBox.setPrefHeight(450);
        if (i > 3){
            showAcountsPane.setPrefHeight(showAcountsPane.getPrefHeight() + 120*(i-3));
            showAcountsVBox.setPrefHeight(showAcountsVBox.getPrefHeight() + 120*(i-3));
        }
    }
    private void ShowGharzeSepordeh(ArrayList<GharzolH> gharzolHS){
        int i = 0;
        for (GharzolH gharz:gharzolHS) {
            if (Objects.equals(gharz.getAccType(), "قرض الحسنه سپرده")){
                Label accTypelbl = new Label("نوع حساب : ");
                Label accNumlbl = new Label("شماره حساب : ");
                Label accStocklbl = new Label("موجودی :(ریال) ");
                Label accTypetxt = new Label(gharz.getAccType());
                Label accNumtxt = new Label(gharz.getAccNumber());
                Label accStocktxt = new Label(String.valueOf(gharz.getAccBalance()));
                accTypelbl.getStyleClass().add("labels");
                accNumlbl.getStyleClass().add("labels");
                accStocklbl.getStyleClass().add("labels");
                accTypetxt.getStyleClass().add("labels");
                accNumtxt.getStyleClass().add("labels");
                accStocktxt.getStyleClass().add("labels");
                VBox vBox1 = new VBox(3);
                VBox vBox2 = new VBox(3);
                vBox1.getChildren().addAll(accTypelbl , accNumlbl , accStocklbl);
                vBox1.setAlignment(Pos.CENTER_RIGHT);
                vBox2.getChildren().addAll(accTypetxt , accNumtxt , accStocktxt);
                BorderPane borderPane = new BorderPane();
                borderPane.setRight(vBox1);
                borderPane.setLeft(vBox2);
                borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
                BorderPane spacePane = new BorderPane();
                spacePane.setStyle("-fx-pref-height: 20");
                showAcountsVBox.getChildren().addAll(borderPane , spacePane);
                i++;
            }
        }
        showAcountsPane.setPrefHeight(450);
        showAcountsVBox.setPrefHeight(450);
        if (i > 3){

            showAcountsPane.setPrefHeight(showAcountsPane.getPrefHeight() + 120*(i-3));
            showAcountsVBox.setPrefHeight(showAcountsVBox.getPrefHeight() + 120*(i-3));
        }
    }
    private void ShowSepordehKotah(ArrayList<Sepordeh> sepordehs){
        int i = 0;
        for (Sepordeh sepordeh: sepordehs) {
            if (Objects.equals(sepordeh.getAccType(), "سپرده کوتاه مدت")){
                Label accTypelbl = new Label("نوع حساب : ");
                Label accNumlbl = new Label("شماره حساب : ");
                Label accStocklbl = new Label("موجودی :(ریال) ");
                Label accInterestlbl = new Label("نرخ سود سالانه : ");
                Label accTermlbl = new Label("تاریخ سررسید سپرده : ");
                Label accTypetxt = new Label(sepordeh.getAccType());
                Label accNumtxt = new Label(sepordeh.getAccNumber());
                Label accStocktxt = new Label(String.valueOf(sepordeh.getAccBalance()));
                Label accInteresttxt = new Label(String.valueOf(sepordeh.getAccInterest()) + "%");
                Label accTermtxt = new Label(String.valueOf(sepordeh.getAccTerminationDate()));
                accTypelbl.getStyleClass().add("labels");
                accNumlbl.getStyleClass().add("labels");
                accStocklbl.getStyleClass().add("labels");
                accInterestlbl.getStyleClass().add("labels");
                accTermlbl.getStyleClass().add("labels");
                accTypetxt.getStyleClass().add("labels");
                accNumtxt.getStyleClass().add("labels");
                accStocktxt.getStyleClass().add("labels");
                accInteresttxt.getStyleClass().add("labels");
                accTermtxt.getStyleClass().add("labels");
                VBox vBox1 = new VBox(5);
                VBox vBox2 = new VBox(5);
                vBox1.getChildren().addAll(accTypelbl , accNumlbl , accStocklbl , accInterestlbl , accTermlbl);
                vBox1.setAlignment(Pos.CENTER_RIGHT);
                vBox2.getChildren().addAll(accTypetxt , accNumtxt , accStocktxt , accInteresttxt , accTermtxt);
                BorderPane borderPane = new BorderPane();
                borderPane.setRight(vBox1);
                borderPane.setLeft(vBox2);
                borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
                BorderPane spacePane = new BorderPane();
                spacePane.setStyle("-fx-pref-height: 20");
                showAcountsVBox.getChildren().addAll(borderPane , spacePane);
                i++;
            }
            showAcountsPane.setPrefHeight(450);
            showAcountsVBox.setPrefHeight(450);
            if (i > 2){
                showAcountsPane.setPrefHeight(showAcountsPane.getPrefHeight() + 150*(i-2));
                showAcountsVBox.setPrefHeight(showAcountsVBox.getPrefHeight() + 120*(i-2));
            }
        }
    }
    private void ShowSepordehBoland(ArrayList<Sepordeh> sepordehs){
        int i = 0;
        for (Sepordeh sepordeh: sepordehs) {
            if (Objects.equals(sepordeh.getAccType(), "سپرده بلند مدت")){
                Label accTypelbl = new Label("نوع حساب : ");
                Label accNumlbl = new Label("شماره حساب : ");
                Label accStocklbl = new Label("موجودی :(ریال) ");
                Label accInterestlbl = new Label("نرخ سود سالانه : ");
                Label accTermlbl = new Label("تاریخ سررسید سپرده : ");
                Label accTypetxt = new Label(sepordeh.getAccType());
                Label accNumtxt = new Label(sepordeh.getAccNumber());
                Label accStocktxt = new Label(String.valueOf(sepordeh.getAccBalance()));
                Label accInteresttxt = new Label(String.valueOf(sepordeh.getAccInterest()) + "%");
                Label accTermtxt = new Label(String.valueOf(sepordeh.getAccTerminationDate()));
                accTypelbl.getStyleClass().add("labels");
                accNumlbl.getStyleClass().add("labels");
                accStocklbl.getStyleClass().add("labels");
                accInterestlbl.getStyleClass().add("labels");
                accTermlbl.getStyleClass().add("labels");
                accTypetxt.getStyleClass().add("labels");
                accNumtxt.getStyleClass().add("labels");
                accStocktxt.getStyleClass().add("labels");
                accInteresttxt.getStyleClass().add("labels");
                accTermtxt.getStyleClass().add("labels");
                VBox vBox1 = new VBox(5);
                VBox vBox2 = new VBox(5);
                vBox1.getChildren().addAll(accTypelbl , accNumlbl , accStocklbl , accInterestlbl , accTermlbl);
                vBox1.setAlignment(Pos.CENTER_RIGHT);
                vBox2.getChildren().addAll(accTypetxt , accNumtxt , accStocktxt , accInteresttxt , accTermtxt);
                BorderPane borderPane = new BorderPane();
                borderPane.setRight(vBox1);
                borderPane.setLeft(vBox2);
                borderPane.getStyleClass().addAll("gray" , "CPBorderPane1");
                BorderPane spacePane = new BorderPane();
                spacePane.setStyle("-fx-pref-height: 20");
                showAcountsVBox.getChildren().addAll(borderPane , spacePane);
                i++;
            }
            showAcountsPane.setPrefHeight(450);
            showAcountsVBox.setPrefHeight(450);
            if (i > 2){
                showAcountsPane.setPrefHeight(showAcountsPane.getPrefHeight() + 150*(i-2));
                showAcountsVBox.setPrefHeight(showAcountsVBox.getPrefHeight() + 120*(i-2));
            }
        }
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


    public void onProfileClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersProfile.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onMessagesClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersMessageBox.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onExitClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/hello-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
        HelloController.userID = 100;
    }

    public void onCreateNewAcountClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CreateAccount.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onDeleteAcountClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/DeleteAccount.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onMoneyTransferClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersMoneyTransfer.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onRequestChequeClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersChequeRequest.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onExportChequeClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/ChequeIssuing.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onManagementChequeClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersManagementIssuedCheque.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();

    }

    public void OnCollectChequeClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersReceiptCheque.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onRequestBankCardClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersCardRequest.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onRequestsHistoryClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersRequestsSituation.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onHistoryClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersTransactionHistory.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onAddSuggestionClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersSuggestion.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onLoanRequestClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersRequestLoan.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onPayLoanClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersLoanPeyments.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onLoanHistoryClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersLoanHistory.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onChargingServiceClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersChargingService.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void onPayingBillsClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPayingBills.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
}
