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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.GharzolH;
import org.example.persianexpress.Objects.Sepordeh;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class EmployeeSeeAccountsController {
    private Stage stage;
    private Scene scene;
    @FXML
    private Button addAccount;
    @FXML
    private ImageView backBtn;
    @FXML
    private ChoiceBox<String> showAccountType;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private VBox mainVBox;
    @FXML
    private Label errorText;
    private Connection connection;
    private PreparedStatement statement;
    private boolean access;

    public void initialize() throws SQLException {

        String[] accountType = new String[]{"همه حساب ها" , "قرض الحسنه جاری" , "قرض الحسنه سپرده" , "سپرده کوتاه مدت" , "سپرده بلند مدت"};
        showAccountType.getItems().addAll(accountType);
        showAccountType.setValue(accountType[0]);

        ArrayList<GharzolH> gharzolHSAcc = new ArrayList<>();
        ArrayList<Sepordeh> sepordehAcc = new ArrayList<>();
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select *from BankAccounts");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            String accType = resultSet.getNString("AccountType");
            if (Objects.equals(accType, "قرض الحسنه جاری") || Objects.equals(accType, "قرض الحسنه سپرده")){
                GharzolH account1 = new GharzolH();
                account1.setAccUID(resultSet.getInt("AccountID"));
                account1.setAccHolderUID(resultSet.getInt("CustomerID"));
                account1.setAccNumber(resultSet.getNString("AccountNumber"));
                account1.setAccType(resultSet.getNString("AccountType"));
                account1.setAccBalance(resultSet.getLong("AccountStock"));
                account1.setAccAccess(resultSet.getBoolean("AccountAccess"));
                gharzolHSAcc.add(account1);
            } else if (Objects.equals(accType, "سپرده کوتاه مدت") || Objects.equals(accType, "سپرده بلند مدت")) {
                Sepordeh account2 = new Sepordeh(resultSet.getInt("AccountID"),resultSet.getInt("CustomerID"));
                account2.setAccNumber(resultSet.getNString("AccountNumber"));
                account2.setAccType(resultSet.getNString("AccountType"));
                account2.setAccBalance(resultSet.getLong("AccountStock"));
                account2.setAccInterest(resultSet.getInt("AccountProfit"));
                account2.setAccTerminationDate(resultSet.getDate("AccountTerm"));
                account2.setAccAccess(resultSet.getBoolean("AccountAccess"));
                sepordehAcc.add(account2);
            }
        }
        int number = gharzolHSAcc.size()+sepordehAcc.size();
        if (number > 2){
            mainPane.setPrefHeight(mainPane.getPrefHeight() + 200*(number - 2));
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 200*(number - 2));
        }
        ShowAllAccounts(gharzolHSAcc , sepordehAcc);
        showAccountType.setOnAction(event -> {
            if (Objects.equals(showAccountType.getValue(), "قرض الحسنه جاری")){
                try {
                    mainVBox.getChildren().clear();
                    ShowGHarzeJari(gharzolHSAcc);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (Objects.equals(showAccountType.getValue(), "قرض الحسنه سپرده")) {
                try {
                    mainVBox.getChildren().clear();
                    ShowGharzeSepordeh(gharzolHSAcc);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else if (Objects.equals(showAccountType.getValue(), "سپرده کوتاه مدت")) {
                try {
                    mainVBox.getChildren().clear();
                    ShowSepordehKotah(sepordehAcc);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else if (Objects.equals(showAccountType.getValue(), "سپرده بلند مدت")) {
                try {
                    mainVBox.getChildren().clear();
                    ShowSepordehBoland(sepordehAcc);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (Objects.equals(showAccountType.getValue(), "همه حساب ها")) {
                try {
                    mainVBox.getChildren().clear();
                    ShowAllAccounts(gharzolHSAcc , sepordehAcc);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void ShowAllAccounts(ArrayList<GharzolH> gharzolHS , ArrayList<Sepordeh> sepordehs) throws SQLException {
        for (GharzolH gharz:gharzolHS) {
            String fullName = null;
            String userN = null;
            statement = connection.prepareStatement("select FirstName , LastName , CustomerUN from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , gharz.getAccHolderUID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                fullName = resultSet.getNString("FirstName") + " " + resultSet.getNString("LastName");
                userN = resultSet.getNString("CustomerUN");
            }
            Label accNumlbl = new Label("شماره حساب :");
            Label accNumtxt = new Label(gharz.getAccNumber());
            Label accTypelbl = new Label("نوع حساب :");
            Label accTypetxt = new Label(gharz.getAccType());
            Label accHolderNamelbl = new Label("نام و نام خانوادگی :");
            Label accHolderNametxt = new Label(fullName);
            Label accHolderUNlbl = new Label("نام کاربری :");
            Label accHolderUNtxt = new Label(userN);
            Label accStocklbl = new Label("موجودی :");
            Label accStocktxt = new Label(String.valueOf(gharz.getAccBalance()));
            Label accAccesslbl = new Label("وضعیت حساب :");
            Label accAccesstxt = new Label();
            if (gharz.isAccAccess()){
                accAccesstxt.setText("فعال");
            } else if (!gharz.isAccAccess()) {
                accAccesstxt.setText("مسدود");
            }
            Button disable = new Button();
            if (gharz.isAccAccess()){
                disable.setText("مسدود کردن");
                disable.getStyleClass().addAll("button1" , "darkblue" , "hover");
            } else if (!gharz.isAccAccess()) {
                disable.setText("فعال کردن");
                disable.getStyleClass().addAll("button1" , "lightblue" , "hover");
            }
            Button delete = new Button("بستن حساب");
            accNumlbl.getStyleClass().add("labels2");
            accNumtxt.getStyleClass().add("labels2");
            accTypelbl.getStyleClass().add("labels2");
            accTypetxt.getStyleClass().add("labels2");
            accHolderNamelbl.getStyleClass().add("labels2");
            accHolderNametxt.getStyleClass().add("labels2");
            accHolderUNlbl.getStyleClass().add("labels2");
            accHolderUNtxt.getStyleClass().add("labels2");
            accStocklbl.getStyleClass().add("labels2");
            accStocktxt.getStyleClass().add("labels2");
            accAccesslbl.getStyleClass().add("labels2");
            accAccesstxt.getStyleClass().add("labels2");
            delete.getStyleClass().addAll("button1" , "darkblue" , "hover");
            VBox vBox1 = new VBox(3);
            VBox vBox2 = new VBox(3);
            VBox vBox3 = new VBox(3);
            VBox vBox4 = new VBox(3);
            HBox hBox1 = new HBox(2);
            HBox hBox2 = new HBox(4);
            vBox1.setAlignment(Pos.BASELINE_RIGHT);
            vBox2.setAlignment(Pos.BASELINE_RIGHT);
            vBox3.setAlignment(Pos.BASELINE_RIGHT);
            vBox4.setAlignment(Pos.BASELINE_RIGHT);
            vBox1.getStyleClass().add("vboxPadding");
            vBox2.getStyleClass().add("vboxPadding");
            vBox3.getStyleClass().add("vboxPadding");
            vBox4.getStyleClass().add("vboxPadding");
            vBox1.getChildren().addAll(accNumlbl , accHolderNamelbl , accStocklbl);
            vBox2.getChildren().addAll(accNumtxt , accHolderNametxt , accStocktxt);
            vBox3.getChildren().addAll(accTypelbl , accHolderUNlbl , accAccesslbl);
            vBox4.getChildren().addAll(accTypetxt , accHolderUNtxt , accAccesstxt);
            hBox1.getChildren().addAll(delete , disable);
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
            disable.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        statement = connection.prepareStatement("select AccountAccess from BankAccounts where AccountID = ?");
                        statement.setInt(1 , gharz.getAccUID());
                        ResultSet resultSet1 = statement.executeQuery();
                        while (resultSet1.next()){
                            access = resultSet1.getBoolean("AccountAccess");
                        }
                        if (access){
                            disable.setText("فعال کردن");
                            disable.setStyle("-fx-background-color: #023e8a");
                            statement = connection.prepareStatement("update BankAccounts set AccountAccess = ? where AccountID = ?");
                            statement.setBoolean( 1 , false);
                            statement.setInt(2 , gharz.getAccUID());
                            int success = statement.executeUpdate();
                            System.out.println(success);
                        }else {
                            disable.setText("مسدود کردن");
                            disable.setStyle("-fx-background-color: #11235A");
                            statement = connection.prepareStatement("update BankAccounts set AccountAccess = ? where AccountID = ?");
                            statement.setBoolean( 1 , true);
                            statement.setInt(2 , gharz.getAccUID());
                            int success = statement.executeUpdate();
                            System.out.println(success);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        boolean access = deleteAccess(gharz.getAccUID());
                        if (access){
                            deleteAccountByEmployee(gharz.getAccUID());
                        }else {
                            errorText.setText("حذف حساب مورد نظر ممکن نمیباشد!");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }
        for (Sepordeh sepordeh: sepordehs) {
            String fullName = null;
            String userN = null;
            statement = connection.prepareStatement("select FirstName , LastName , CustomerUN from CustomersInfo where CustomerID = ?");
            statement.setInt(1 , sepordeh.getAccHolderUID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                fullName = resultSet.getNString("FirstName") + " " + resultSet.getNString("LastName");
                userN = resultSet.getNString("CustomerUN");
            }
            Label accNumlbl = new Label("شماره حساب :");
            Label accNumtxt = new Label(sepordeh.getAccNumber());
            Label accTypelbl = new Label("نوع حساب :");
            Label accTypetxt = new Label(sepordeh.getAccType());
            Label accHolderNamelbl = new Label("نام و نام خانوادگی :");
            Label accHolderNametxt = new Label(fullName);
            Label accHolderUNlbl = new Label("نام کاربری :");
            Label accHolderUNtxt = new Label(userN);
            Label accStocklbl = new Label("موجودی :");
            Label accStocktxt = new Label(String.valueOf(sepordeh.getAccBalance()));
            Label accProfitlbl = new Label("نرخ سود :");
            Label accProfittxt = new Label(String.valueOf(sepordeh.getAccInterest()));
            Label accTermlbl = new Label("تاریخ انقضا :");
            Label accTermtxt = new Label(String.valueOf(sepordeh.getAccTerminationDate()));
            Label accAccesslbl = new Label("وضعیت حساب :");
            Label accAccesstxt = new Label();
            if (sepordeh.isAccAccess()){
                accAccesstxt.setText("فعال");
            } else if (!sepordeh.isAccAccess()) {
                accAccesstxt.setText("مسدود");
            }
            Button disable = new Button();
            if (sepordeh.isAccAccess()){
                disable.setText("مسدود کردن");
                disable.getStyleClass().addAll("button1" , "darkblue" , "hover");
            } else if (!sepordeh.isAccAccess()) {
                disable.setText("فعال کردن");
                disable.getStyleClass().addAll("button1" , "lightblue" , "hover");
            }
            Button delete = new Button("بستن حساب");
            accNumlbl.getStyleClass().add("labels2");
            accNumtxt.getStyleClass().add("labels2");
            accTypelbl.getStyleClass().add("labels2");
            accTypetxt.getStyleClass().add("labels2");
            accHolderNamelbl.getStyleClass().add("labels2");
            accHolderNametxt.getStyleClass().add("labels2");
            accHolderUNlbl.getStyleClass().add("labels2");
            accHolderUNtxt.getStyleClass().add("labels2");
            accStocklbl.getStyleClass().add("labels2");
            accStocktxt.getStyleClass().add("labels2");
            accProfitlbl.getStyleClass().add("labels2");
            accProfittxt.getStyleClass().add("labels2");
            accTermlbl.getStyleClass().add("labels2");
            accTermtxt.getStyleClass().add("labels2");
            accAccesslbl.getStyleClass().add("labels2");
            accAccesstxt.getStyleClass().add("labels2");
            delete.getStyleClass().addAll("button1" , "darkblue" , "hover");
            VBox vBox1 = new VBox(4);
            VBox vBox2 = new VBox(4);
            VBox vBox3 = new VBox(4);
            VBox vBox4 = new VBox(4);
            HBox hBox1 = new HBox(2);
            HBox hBox2 = new HBox(4);
            vBox1.setAlignment(Pos.BASELINE_RIGHT);
            vBox2.setAlignment(Pos.BASELINE_RIGHT);
            vBox3.setAlignment(Pos.BASELINE_RIGHT);
            vBox4.setAlignment(Pos.BASELINE_RIGHT);
            vBox1.getStyleClass().add("vboxPadding");
            vBox2.getStyleClass().add("vboxPadding");
            vBox3.getStyleClass().add("vboxPadding");
            vBox4.getStyleClass().add("vboxPadding");
            vBox1.getChildren().addAll(accNumlbl , accHolderNamelbl , accStocklbl , accTermlbl);
            vBox2.getChildren().addAll(accNumtxt , accHolderNametxt , accStocktxt , accTermtxt);
            vBox3.getChildren().addAll(accTypelbl , accHolderUNlbl , accProfitlbl , accAccesslbl);
            vBox4.getChildren().addAll(accTypetxt , accHolderUNtxt , accProfittxt , accAccesstxt);
            hBox1.getChildren().addAll(delete , disable);
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
            disable.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        statement = connection.prepareStatement("select AccountAccess from BankAccounts where AccountID = ?");
                        statement.setInt(1 , sepordeh.getAccUID());
                        ResultSet resultSet1 = statement.executeQuery();
                        while (resultSet1.next()){
                            access = resultSet1.getBoolean("AccountAccess");
                        }
                        if (access){
                            disable.setText("فعال کردن");
                            disable.setStyle("-fx-background-color: #023e8a");
                            statement = connection.prepareStatement("update BankAccounts set AccountAccess = ? where AccountID = ?");
                            statement.setBoolean( 1 , false);
                            statement.setInt(2 , sepordeh.getAccUID());
                            int success = statement.executeUpdate();
                            System.out.println(success);
                        }else {
                            disable.setText("مسدود کردن");
                            disable.setStyle("-fx-background-color: #11235A");
                            statement = connection.prepareStatement("update BankAccounts set AccountAccess = ? where AccountID = ?");
                            statement.setBoolean( 1 , true);
                            statement.setInt(2 , sepordeh.getAccUID());
                            int success = statement.executeUpdate();
                            System.out.println(success);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        boolean access = deleteAccess(sepordeh.getAccUID());
                        if (access){
                            deleteAccountByEmployee(sepordeh.getAccUID());
                        }else {
                            errorText.setText("حذف حساب مورد نظر ممکن نمیباشد!");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
        }
    }
    private void ShowGHarzeJari(ArrayList<GharzolH> gharzolHS) throws SQLException {
        for (GharzolH gharz:gharzolHS) {
            if (Objects.equals(gharz.getAccType(), "قرض الحسنه جاری")){
                String fullName = null;
                String userN = null;
                statement = connection.prepareStatement("select FirstName , LastName , CustomerUN from CustomersInfo where CustomerID = ?");
                statement.setInt(1 , gharz.getAccHolderUID());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    fullName = resultSet.getNString("FirstName") + " " + resultSet.getNString("LastName");
                    userN = resultSet.getNString("CustomerUN");
                }
                Label accNumlbl = new Label("شماره حساب :");
                Label accNumtxt = new Label(gharz.getAccNumber());
                Label accTypelbl = new Label("نوع حساب :");
                Label accTypetxt = new Label(gharz.getAccType());
                Label accHolderNamelbl = new Label("نام و نام خانوادگی :");
                Label accHolderNametxt = new Label(fullName);
                Label accHolderUNlbl = new Label("نام کاربری :");
                Label accHolderUNtxt = new Label(userN);
                Label accStocklbl = new Label("موجودی :");
                Label accStocktxt = new Label(String.valueOf(gharz.getAccBalance()));
                Label accAccesslbl = new Label("وضعیت حساب :");
                Label accAccesstxt = new Label();
                if (gharz.isAccAccess()){
                    accAccesstxt.setText("فعال");
                } else if (!gharz.isAccAccess()) {
                    accAccesstxt.setText("مسدود");
                }
                Button disable = new Button();
                if (gharz.isAccAccess()){
                    disable.setText("مسدود کردن");
                    disable.getStyleClass().addAll("button1" , "darkblue" , "hover");
                } else if (!gharz.isAccAccess()) {
                    disable.setText("فعال کردن");
                    disable.getStyleClass().addAll("button1" , "lightblue" , "hover");
                }
                Button delete = new Button("بستن حساب");
                accNumlbl.getStyleClass().add("labels2");
                accNumtxt.getStyleClass().add("labels2");
                accTypelbl.getStyleClass().add("labels2");
                accTypetxt.getStyleClass().add("labels2");
                accHolderNamelbl.getStyleClass().add("labels2");
                accHolderNametxt.getStyleClass().add("labels2");
                accHolderUNlbl.getStyleClass().add("labels2");
                accHolderUNtxt.getStyleClass().add("labels2");
                accStocklbl.getStyleClass().add("labels2");
                accStocktxt.getStyleClass().add("labels2");
                accAccesslbl.getStyleClass().add("labels2");
                accAccesstxt.getStyleClass().add("labels2");
                delete.getStyleClass().addAll("button1" , "darkblue" , "hover");
                VBox vBox1 = new VBox(3);
                VBox vBox2 = new VBox(3);
                VBox vBox3 = new VBox(3);
                VBox vBox4 = new VBox(3);
                HBox hBox1 = new HBox(2);
                HBox hBox2 = new HBox(4);
                vBox1.setAlignment(Pos.BASELINE_RIGHT);
                vBox2.setAlignment(Pos.BASELINE_RIGHT);
                vBox3.setAlignment(Pos.BASELINE_RIGHT);
                vBox4.setAlignment(Pos.BASELINE_RIGHT);
                vBox1.getStyleClass().add("vboxPadding");
                vBox2.getStyleClass().add("vboxPadding");
                vBox3.getStyleClass().add("vboxPadding");
                vBox4.getStyleClass().add("vboxPadding");
                vBox1.getChildren().addAll(accNumlbl , accHolderNamelbl , accStocklbl);
                vBox2.getChildren().addAll(accNumtxt , accHolderNametxt , accStocktxt);
                vBox3.getChildren().addAll(accTypelbl , accHolderUNlbl , accAccesslbl);
                vBox4.getChildren().addAll(accTypetxt , accHolderUNtxt , accAccesstxt);
                hBox1.getChildren().addAll(delete , disable);
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
                disable.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            statement = connection.prepareStatement("select AccountAccess from BankAccounts where AccountID = ?");
                            statement.setInt(1 , gharz.getAccUID());
                            ResultSet resultSet1 = statement.executeQuery();
                            while (resultSet1.next()){
                                access = resultSet1.getBoolean("AccountAccess");
                            }
                            if (access){
                                disable.setText("فعال کردن");
                                disable.setStyle("-fx-background-color: #023e8a");
                                statement = connection.prepareStatement("update BankAccounts set AccountAccess = ? where AccountID = ?");
                                statement.setBoolean( 1 , false);
                                statement.setInt(2 , gharz.getAccUID());
                                int success = statement.executeUpdate();
                                System.out.println(success);
                            }else {
                                disable.setText("مسدود کردن");
                                disable.setStyle("-fx-background-color: #11235A");
                                statement = connection.prepareStatement("update BankAccounts set AccountAccess = ? where AccountID = ?");
                                statement.setBoolean( 1 , true);
                                statement.setInt(2 , gharz.getAccUID());
                                int success = statement.executeUpdate();
                                System.out.println(success);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            boolean access = deleteAccess(gharz.getAccUID());
                            if (access){
                                deleteAccountByEmployee(gharz.getAccUID());
                            }else {
                                errorText.setText("حذف حساب مورد نظر ممکن نمیباشد!");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
            }
        }
    }
    private void ShowGharzeSepordeh(ArrayList<GharzolH> gharzolHS) throws SQLException {
        for (GharzolH gharz:gharzolHS) {
            if (Objects.equals(gharz.getAccType(), "قرض الحسنه سپرده")){
                String fullName = null;
                String userN = null;
                statement = connection.prepareStatement("select FirstName , LastName , CustomerUN from CustomersInfo where CustomerID = ?");
                statement.setInt(1 , gharz.getAccHolderUID());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    fullName = resultSet.getNString("FirstName") + " " + resultSet.getNString("LastName");
                    userN = resultSet.getNString("CustomerUN");
                }
                Label accNumlbl = new Label("شماره حساب :");
                Label accNumtxt = new Label(gharz.getAccNumber());
                Label accTypelbl = new Label("نوع حساب :");
                Label accTypetxt = new Label(gharz.getAccType());
                Label accHolderNamelbl = new Label("نام و نام خانوادگی :");
                Label accHolderNametxt = new Label(fullName);
                Label accHolderUNlbl = new Label("نام کاربری :");
                Label accHolderUNtxt = new Label(userN);
                Label accStocklbl = new Label("موجودی :");
                Label accStocktxt = new Label(String.valueOf(gharz.getAccBalance()));
                Label accAccesslbl = new Label("وضعیت حساب :");
                Label accAccesstxt = new Label();
                if (gharz.isAccAccess()){
                    accAccesstxt.setText("فعال");
                } else if (!gharz.isAccAccess()) {
                    accAccesstxt.setText("مسدود");
                }
                Button disable = new Button();
                if (gharz.isAccAccess()){
                    disable.setText("مسدود کردن");
                    disable.getStyleClass().addAll("button1" , "darkblue" , "hover");
                } else if (!gharz.isAccAccess()) {
                    disable.setText("فعال کردن");
                    disable.getStyleClass().addAll("button1" , "lightblue" , "hover");
                }
                Button delete = new Button("بستن حساب");
                accNumlbl.getStyleClass().add("labels2");
                accNumtxt.getStyleClass().add("labels2");
                accTypelbl.getStyleClass().add("labels2");
                accTypetxt.getStyleClass().add("labels2");
                accHolderNamelbl.getStyleClass().add("labels2");
                accHolderNametxt.getStyleClass().add("labels2");
                accHolderUNlbl.getStyleClass().add("labels2");
                accHolderUNtxt.getStyleClass().add("labels2");
                accStocklbl.getStyleClass().add("labels2");
                accStocktxt.getStyleClass().add("labels2");
                accAccesslbl.getStyleClass().add("labels2");
                accAccesstxt.getStyleClass().add("labels2");
                delete.getStyleClass().addAll("button1" , "darkblue" , "hover");
                VBox vBox1 = new VBox(3);
                VBox vBox2 = new VBox(3);
                VBox vBox3 = new VBox(3);
                VBox vBox4 = new VBox(3);
                HBox hBox1 = new HBox(2);
                HBox hBox2 = new HBox(4);
                vBox1.setAlignment(Pos.BASELINE_RIGHT);
                vBox2.setAlignment(Pos.BASELINE_RIGHT);
                vBox3.setAlignment(Pos.BASELINE_RIGHT);
                vBox4.setAlignment(Pos.BASELINE_RIGHT);
                vBox1.getStyleClass().add("vboxPadding");
                vBox2.getStyleClass().add("vboxPadding");
                vBox3.getStyleClass().add("vboxPadding");
                vBox4.getStyleClass().add("vboxPadding");
                vBox1.getChildren().addAll(accNumlbl , accHolderNamelbl , accStocklbl);
                vBox2.getChildren().addAll(accNumtxt , accHolderNametxt , accStocktxt);
                vBox3.getChildren().addAll(accTypelbl , accHolderUNlbl , accAccesslbl);
                vBox4.getChildren().addAll(accTypetxt , accHolderUNtxt , accAccesstxt);
                hBox1.getChildren().addAll(delete , disable);
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
                disable.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            statement = connection.prepareStatement("select AccountAccess from BankAccounts where AccountID = ?");
                            statement.setInt(1 , gharz.getAccUID());
                            ResultSet resultSet1 = statement.executeQuery();
                            while (resultSet1.next()){
                                access = resultSet1.getBoolean("AccountAccess");
                            }
                            if (access){
                                disable.setText("فعال کردن");
                                disable.setStyle("-fx-background-color: #023e8a");
                                statement = connection.prepareStatement("update BankAccounts set AccountAccess = ? where AccountID = ?");
                                statement.setBoolean( 1 , false);
                                statement.setInt(2 , gharz.getAccUID());
                                int success = statement.executeUpdate();
                                System.out.println(success);
                            }else {
                                disable.setText("مسدود کردن");
                                disable.setStyle("-fx-background-color: #11235A");
                                statement = connection.prepareStatement("update BankAccounts set AccountAccess = ? where AccountID = ?");
                                statement.setBoolean( 1 , true);
                                statement.setInt(2 , gharz.getAccUID());
                                int success = statement.executeUpdate();
                                System.out.println(success);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            boolean access = deleteAccess(gharz.getAccUID());
                            if (access){
                                deleteAccountByEmployee(gharz.getAccUID());
                            }else {
                                errorText.setText("حذف حساب مورد نظر ممکن نمیباشد!");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
            }
        }
    }
    private void ShowSepordehKotah(ArrayList<Sepordeh> sepordehs) throws SQLException {
        for (Sepordeh sepordeh: sepordehs) {
            if (Objects.equals(sepordeh.getAccType(), "سپرده کوتاه مدت")){
                String fullName = null;
                String userN = null;
                statement = connection.prepareStatement("select FirstName , LastName , CustomerUN from CustomersInfo where CustomerID = ?");
                statement.setInt(1 , sepordeh.getAccHolderUID());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    fullName = resultSet.getNString("FirstName") + " " + resultSet.getNString("LastName");
                    userN = resultSet.getNString("CustomerUN");
                }
                Label accNumlbl = new Label("شماره حساب :");
                Label accNumtxt = new Label(sepordeh.getAccNumber());
                Label accTypelbl = new Label("نوع حساب :");
                Label accTypetxt = new Label(sepordeh.getAccType());
                Label accHolderNamelbl = new Label("نام و نام خانوادگی :");
                Label accHolderNametxt = new Label(fullName);
                Label accHolderUNlbl = new Label("نام کاربری :");
                Label accHolderUNtxt = new Label(userN);
                Label accStocklbl = new Label("موجودی :");
                Label accStocktxt = new Label(String.valueOf(sepordeh.getAccBalance()));
                Label accProfitlbl = new Label("نرخ سود :");
                Label accProfittxt = new Label(String.valueOf(sepordeh.getAccInterest()));
                Label accTermlbl = new Label("تاریخ انقضا :");
                Label accTermtxt = new Label(String.valueOf(sepordeh.getAccTerminationDate()));
                Label accAccesslbl = new Label("وضعیت حساب :");
                Label accAccesstxt = new Label();
                if (sepordeh.isAccAccess()){
                    accAccesstxt.setText("فعال");
                } else if (!sepordeh.isAccAccess()) {
                    accAccesstxt.setText("مسدود");
                }
                Button disable = new Button();
                if (sepordeh.isAccAccess()){
                    disable.setText("مسدود کردن");
                    disable.getStyleClass().addAll("button1" , "darkblue" , "hover");
                } else if (!sepordeh.isAccAccess()) {
                    disable.setText("فعال کردن");
                    disable.getStyleClass().addAll("button1" , "lightblue" , "hover");
                }
                Button delete = new Button("بستن حساب");
                accNumlbl.getStyleClass().add("labels2");
                accNumtxt.getStyleClass().add("labels2");
                accTypelbl.getStyleClass().add("labels2");
                accTypetxt.getStyleClass().add("labels2");
                accHolderNamelbl.getStyleClass().add("labels2");
                accHolderNametxt.getStyleClass().add("labels2");
                accHolderUNlbl.getStyleClass().add("labels2");
                accHolderUNtxt.getStyleClass().add("labels2");
                accStocklbl.getStyleClass().add("labels2");
                accStocktxt.getStyleClass().add("labels2");
                accProfitlbl.getStyleClass().add("labels2");
                accProfittxt.getStyleClass().add("labels2");
                accTermlbl.getStyleClass().add("labels2");
                accTermtxt.getStyleClass().add("labels2");
                accAccesslbl.getStyleClass().add("labels2");
                accAccesstxt.getStyleClass().add("labels2");
                delete.getStyleClass().addAll("button1" , "darkblue" , "hover");
                VBox vBox1 = new VBox(4);
                VBox vBox2 = new VBox(4);
                VBox vBox3 = new VBox(4);
                VBox vBox4 = new VBox(4);
                HBox hBox1 = new HBox(2);
                HBox hBox2 = new HBox(4);
                vBox1.setAlignment(Pos.BASELINE_RIGHT);
                vBox2.setAlignment(Pos.BASELINE_RIGHT);
                vBox3.setAlignment(Pos.BASELINE_RIGHT);
                vBox4.setAlignment(Pos.BASELINE_RIGHT);
                vBox1.getStyleClass().add("vboxPadding");
                vBox2.getStyleClass().add("vboxPadding");
                vBox3.getStyleClass().add("vboxPadding");
                vBox4.getStyleClass().add("vboxPadding");
                vBox1.getChildren().addAll(accNumlbl , accHolderNamelbl , accStocklbl , accTermlbl);
                vBox2.getChildren().addAll(accNumtxt , accHolderNametxt , accStocktxt , accTermtxt);
                vBox3.getChildren().addAll(accTypelbl , accHolderUNlbl , accProfitlbl , accAccesslbl);
                vBox4.getChildren().addAll(accTypetxt , accHolderUNtxt , accProfittxt , accAccesstxt);
                hBox1.getChildren().addAll(delete , disable);
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
                disable.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            statement = connection.prepareStatement("select AccountAccess from BankAccounts where AccountID = ?");
                            statement.setInt(1 , sepordeh.getAccUID());
                            ResultSet resultSet1 = statement.executeQuery();
                            while (resultSet1.next()){
                                access = resultSet1.getBoolean("AccountAccess");
                            }
                            if (access){
                                disable.setText("فعال کردن");
                                disable.setStyle("-fx-background-color: #023e8a");
                                statement = connection.prepareStatement("update BankAccounts set AccountAccess = ? where AccountID = ?");
                                statement.setBoolean( 1 , false);
                                statement.setInt(2 , sepordeh.getAccUID());
                                int success = statement.executeUpdate();
                                System.out.println(success);
                            }else {
                                disable.setText("مسدود کردن");
                                disable.setStyle("-fx-background-color: #11235A");
                                statement = connection.prepareStatement("update BankAccounts set AccountAccess = ? where AccountID = ?");
                                statement.setBoolean( 1 , true);
                                statement.setInt(2 , sepordeh.getAccUID());
                                int success = statement.executeUpdate();
                                System.out.println(success);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            boolean access = deleteAccess(sepordeh.getAccUID());
                            if (access){
                                deleteAccountByEmployee(sepordeh.getAccUID());
                            }else {
                                errorText.setText("حذف حساب مورد نظر ممکن نمیباشد!");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
            }
        }
    }
    private void ShowSepordehBoland(ArrayList<Sepordeh> sepordehs) throws SQLException {
        for (Sepordeh sepordeh: sepordehs) {
            if (Objects.equals(sepordeh.getAccType(), "سپرده بلند مدت")){
                String fullName = null;
                String userN = null;
                statement = connection.prepareStatement("select FirstName , LastName , CustomerUN from CustomersInfo where CustomerID = ?");
                statement.setInt(1 , sepordeh.getAccHolderUID());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    fullName = resultSet.getNString("FirstName") + " " + resultSet.getNString("LastName");
                    userN = resultSet.getNString("CustomerUN");
                }
                Label accNumlbl = new Label("شماره حساب :");
                Label accNumtxt = new Label(sepordeh.getAccNumber());
                Label accTypelbl = new Label("نوع حساب :");
                Label accTypetxt = new Label(sepordeh.getAccType());
                Label accHolderNamelbl = new Label("نام و نام خانوادگی :");
                Label accHolderNametxt = new Label(fullName);
                Label accHolderUNlbl = new Label("نام کاربری :");
                Label accHolderUNtxt = new Label(userN);
                Label accStocklbl = new Label("موجودی :");
                Label accStocktxt = new Label(String.valueOf(sepordeh.getAccBalance()));
                Label accProfitlbl = new Label("نرخ سود :");
                Label accProfittxt = new Label(String.valueOf(sepordeh.getAccInterest()));
                Label accTermlbl = new Label("تاریخ انقضا :");
                Label accTermtxt = new Label(String.valueOf(sepordeh.getAccTerminationDate()));
                Label accAccesslbl = new Label("وضعیت حساب :");
                Label accAccesstxt = new Label();
                if (sepordeh.isAccAccess()){
                    accAccesstxt.setText("فعال");
                } else if (!sepordeh.isAccAccess()) {
                    accAccesstxt.setText("مسدود");
                }
                Button disable = new Button();
                if (sepordeh.isAccAccess()){
                    disable.setText("مسدود کردن");
                    disable.getStyleClass().addAll("button1" , "darkblue" , "hover");
                } else if (!sepordeh.isAccAccess()) {
                    disable.setText("فعال کردن");
                    disable.getStyleClass().addAll("button1" , "lightblue" , "hover");
                }
                Button delete = new Button("بستن حساب");
                accNumlbl.getStyleClass().add("labels2");
                accNumtxt.getStyleClass().add("labels2");
                accTypelbl.getStyleClass().add("labels2");
                accTypetxt.getStyleClass().add("labels2");
                accHolderNamelbl.getStyleClass().add("labels2");
                accHolderNametxt.getStyleClass().add("labels2");
                accHolderUNlbl.getStyleClass().add("labels2");
                accHolderUNtxt.getStyleClass().add("labels2");
                accStocklbl.getStyleClass().add("labels2");
                accStocktxt.getStyleClass().add("labels2");
                accProfitlbl.getStyleClass().add("labels2");
                accProfittxt.getStyleClass().add("labels2");
                accTermlbl.getStyleClass().add("labels2");
                accTermtxt.getStyleClass().add("labels2");
                accAccesslbl.getStyleClass().add("labels2");
                accAccesstxt.getStyleClass().add("labels2");
                delete.getStyleClass().addAll("button1" , "darkblue" , "hover");
                VBox vBox1 = new VBox(4);
                VBox vBox2 = new VBox(4);
                VBox vBox3 = new VBox(4);
                VBox vBox4 = new VBox(4);
                HBox hBox1 = new HBox(2);
                HBox hBox2 = new HBox(4);
                vBox1.setAlignment(Pos.BASELINE_RIGHT);
                vBox2.setAlignment(Pos.BASELINE_RIGHT);
                vBox3.setAlignment(Pos.BASELINE_RIGHT);
                vBox4.setAlignment(Pos.BASELINE_RIGHT);
                vBox1.getStyleClass().add("vboxPadding");
                vBox2.getStyleClass().add("vboxPadding");
                vBox3.getStyleClass().add("vboxPadding");
                vBox4.getStyleClass().add("vboxPadding");
                vBox1.getChildren().addAll(accNumlbl , accHolderNamelbl , accStocklbl , accTermlbl);
                vBox2.getChildren().addAll(accNumtxt , accHolderNametxt , accStocktxt , accTermtxt);
                vBox3.getChildren().addAll(accTypelbl , accHolderUNlbl , accProfitlbl , accAccesslbl);
                vBox4.getChildren().addAll(accTypetxt , accHolderUNtxt , accProfittxt , accAccesstxt);
                hBox1.getChildren().addAll(delete , disable);
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
                disable.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            statement = connection.prepareStatement("select AccountAccess from BankAccounts where AccountID = ?");
                            statement.setInt(1 , sepordeh.getAccUID());
                            ResultSet resultSet1 = statement.executeQuery();
                            while (resultSet1.next()){
                                access = resultSet1.getBoolean("AccountAccess");
                            }
                            if (access){
                                disable.setText("فعال کردن");
                                disable.setStyle("-fx-background-color: #023e8a");
                                statement = connection.prepareStatement("update BankAccounts set AccountAccess = ? where AccountID = ?");
                                statement.setBoolean( 1 , false);
                                statement.setInt(2 , sepordeh.getAccUID());
                                int success = statement.executeUpdate();
                                System.out.println(success);
                            }else {
                                disable.setText("مسدود کردن");
                                disable.setStyle("-fx-background-color: #11235A");
                                statement = connection.prepareStatement("update BankAccounts set AccountAccess = ? where AccountID = ?");
                                statement.setBoolean( 1 , true);
                                statement.setInt(2 , sepordeh.getAccUID());
                                int success = statement.executeUpdate();
                                System.out.println(success);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            boolean access = deleteAccess(sepordeh.getAccUID());
                            if (access){
                                deleteAccountByEmployee(sepordeh.getAccUID());
                            }else {
                                errorText.setText("حذف حساب مورد نظر ممکن نمیباشد!");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                mainVBox.getChildren().addAll(borderPane1 , borderPane2 , spacePane);
            }
        }
    }
    private boolean deleteAccess(int AccountID) throws SQLException {
        boolean access = true;
        statement = connection.prepareStatement("select AccountID , LoanInstalments from LoansHistory where AccountID = ?" , ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1 , AccountID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            resultSet.beforeFirst();
            while (resultSet.next()){
                if (resultSet.getInt("LoanIstalments") == 0){
                    access = true;
                } else if (resultSet.getInt("LoanIstalments") != 0) {
                    access = false;
                }
            }
        }else {
            access = true;
        }
        statement = connection.prepareStatement("select AccountID , ChequeStatus from ChequeHistory where AccountID = ?" , ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1 , AccountID);
        resultSet = statement.executeQuery();
        if (access){
            if (resultSet.next()){
                resultSet.beforeFirst();
                while (resultSet.next()){
                    if (Objects.equals(resultSet.getNString("ChequeStatus"), "وصول شده")){
                        access = true;
                    } else if (Objects.equals(resultSet.getNString("ChequeStatus"), "وصول نشده") || Objects.equals(resultSet.getNString("ChequeStatus"), "برگشت خورده")) {
                        access = false;
                    }
                }
            }
        }
        return access;
    }
    private void deleteAccountByEmployee(int AccID) throws SQLException {
        statement = connection.prepareStatement("delete from BankAccounts where AccountID = ?");
        statement.setInt(1 , AccID);
        int success1 = statement.executeUpdate();

        statement = connection.prepareStatement("delete from BankCardREQ where SelectedAccountID = ?");
        statement.setInt(1 , AccID);
        int success2 = statement.executeUpdate();

        statement = connection.prepareStatement("delete from BankCards where AccountID = ?");
        statement.setInt(1 , AccID);
        int success3 = statement.executeUpdate();

        statement = connection.prepareStatement("delete from CheckBookREQ where AccountID = ?");
        statement.setInt(1 , AccID);
        int success4 = statement.executeUpdate();

        statement = connection.prepareStatement("delete from ChequeHistory where AccountID = ?");
        statement.setInt(1 , AccID);
        int success5 = statement.executeUpdate();

        statement = connection.prepareStatement("delete from DeleteAccountREQ where AccountID = ?");
        statement.setInt(1 , AccID);
        int success6 = statement.executeUpdate();

        statement = connection.prepareStatement("delete from LoanREQ where SelectedAccountID = ?");
        statement.setInt(1 , AccID);
        int success7 = statement.executeUpdate();

        statement = connection.prepareStatement("delete from ReceiptChequeREQ where AccountID = ?");
        statement.setInt(1 , AccID);
        int success8 = statement.executeUpdate();

        statement = connection.prepareStatement("delete from StockCheque where AccountID = ?");
        statement.setInt(1 , AccID);
        int success9 = statement.executeUpdate();
    }

    @FXML
    private void onAddAccountClicked(ActionEvent event){

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
