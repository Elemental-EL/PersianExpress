package org.example.persianexpress;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class CustomersLoanPeymentsController {
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private VBox mainVBox;
    @FXML
    private ChoiceBox<String> selectLoan;
    @FXML
    private Label errortxt;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private ResultSet updateRes;
    private ArrayList<Integer> accID = new ArrayList<>();
    private ArrayList<String> loanType = new ArrayList<>();
    private int instalments = 0;
    private int instalment = 0;
    private int instalment1 = 0;
    private long amount = 0;
    private Date loanDate;
    private String type;

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "sa" , "hmnxt");
        statement = connection.prepareStatement("select AccountID , LoanType from LoansHistory where CustomerID = ? And LoanInstalments != 0");
        statement.setInt(1 , HelloController.userID);
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            accID.add(resultSet.getInt("AccountID"));
            loanType.add(resultSet.getNString("LoanType"));
        }
        selectLoan.getItems().clear();
        selectLoan.getItems().add("انتخاب کنید");
        selectLoan.setValue("انتخاب کنید");
        selectLoan.getItems().addAll(loanType);
        selectLoan.setOnAction(event -> {
            if (Objects.equals(selectLoan.getValue(), "انتخاب کنید")){
                mainVBox.getChildren().clear();
                mainVBox.setPrefHeight(500);
                mainAnchorPane.setPrefHeight(500);
            }else {
                int index = loanType.indexOf(selectLoan.getValue());
                try {
                    ShowPeyment(accID.get(index));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void ShowPeyment(int ID) throws SQLException {
        statement = connection.prepareStatement("select *from LoansHistory where AccountID = ?");
        statement.setInt(1 , ID);
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            instalments = resultSet.getInt("LoanInstalments");
            amount = resultSet.getLong("RemainingAmount");
            loanDate = resultSet.getDate("LoanDate");
            type = resultSet.getNString("LoanType");
            if (Objects.equals(type, "وام قرض الحسنه")){
                instalment = 36;
            } else if (type == "وام دانشجویی") {
                instalment = 12;
            } else if (type == "وام ازدواج") {
                instalment = 60 ;
            } else if (type == "وام مسکن") {
                instalment = 120;
            }
        }
        mainVBox.setPrefHeight(500);
        mainAnchorPane.setPrefHeight(500);
        if (instalments > 2){
            mainVBox.setPrefHeight(mainVBox.getPrefHeight() + 200 * (instalments - 2));
            mainAnchorPane.setPrefHeight(mainAnchorPane.getPrefHeight() + 200 * (instalments-2));
        }
        instalment1 = instalment - instalments;
        int j = 1;
        for (int i = instalment1 ; i < instalment ; i++){
            Label payIDlbl = new Label("شماره قسط :");
            Label payIDtxt = new Label(String.valueOf(instalment1 + j));
            Label payDatelbl = new Label("تاریخ پرداخت قسط :");
            Label payDatetxt = new Label(String.valueOf(loanDate.toLocalDate().plusMonths(j)));
            Label payAmountlbl = new Label("مبلغ قسط :");
            Label payAmounttxt = new Label(String.valueOf(amount / instalments) + " " + "تومان");
            Button paybtn = new Button("پرداخت");
            payIDlbl.getStyleClass().add("labels1");
            payIDtxt.getStyleClass().add("labels1");
            payDatelbl.getStyleClass().add("labels1");
            payDatetxt.getStyleClass().add("labels1");
            payAmountlbl.getStyleClass().add("labels1");
            payAmounttxt.getStyleClass().add("labels1");
            paybtn.getStyleClass().addAll("darkblue" , "hover");
            paybtn.setStyle("-fx-text-fill: #fff; -fx-font-size: 16;");
            VBox vBox1 = new VBox(3);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            VBox vBox2 = new VBox(3);
            vBox1.getChildren().addAll(payIDlbl , payDatelbl , payAmountlbl);
            vBox2.getChildren().addAll(payIDtxt , payDatetxt , payAmounttxt);
            BorderPane pane1 = new BorderPane();
            pane1.setRight(vBox1);
            pane1.setLeft(vBox2);
            BorderPane pane2 = new BorderPane();
            pane1.getStyleClass().addAll("gray" , "CPBorderPane1");
            pane2.getStyleClass().addAll("gray" , "CPBorderPane1");
            pane2.setCenter(paybtn);
            BorderPane spacePane = new BorderPane();
            spacePane.setStyle("-fx-pref-height: 10; -fx-background-color: #fff");
            mainVBox.getChildren().addAll(pane1 , pane2 , spacePane);
            pane2.setId(String.valueOf(i));
            j++;
            paybtn.setOnAction(event -> {
                if (Objects.equals(pane2.getId(), String.valueOf(instalment1))){
                    errortxt.setText(" ");
                    try {
                        long profit = 0 ;
                        statement = connection.prepareStatement("select AccountStock from BankAccounts where AccountID = ?");
                        statement.setInt(1 , ID);
                        resultSet = statement.executeQuery();
                        while (resultSet.next()){
                            profit = resultSet.getLong("AccountStock");
                        }
                        if (profit >= (amount / instalments)){
                            statement = connection.prepareStatement("UPDATE BankAccounts SET AccountStock = ? WHERE AccountID = ?");
                            statement.setLong(1 , profit - (amount / instalments));
                            statement.setInt(2 , ID);
                            int succes = statement.executeUpdate();
                            statement = connection.prepareStatement("UPDATE LoansHistory SET LoanInstalments = ? , RemainingAmount = ? where AccountID = ?");
                            statement.setInt(1 , instalments - 1);
                            statement.setLong(2 , amount - (amount / instalments));
                            statement.setInt(3 , ID);
                            int succes1 = statement.executeUpdate();
                            mainVBox.getChildren().clear();

                            ShowPeyment(ID);
                        }else{
                            errortxt.setText("موجودی حساب شما کافی نمیباشد !!");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    errortxt.setText("امکان پرداخت این قسط وجود ندارد !");
                }
            });
        }
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
}
