package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.GharzolH;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class DeleteAccountController {
    private Stage stage;
    private Scene scene;
    @FXML
    private ChoiceBox<String> SelectedAccount , AlternativeAccount;
    private ArrayList <String> BankAccount=new ArrayList<String>();
    private ArrayList <Integer> BankAccountID=new ArrayList<Integer>();
    @FXML
    private Label errorText;
    private java.sql.Date currentDate = Date.valueOf(LocalDate.now());
    Connection connection;
    PreparedStatement statement;
    public void initialize()throws SQLException{
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-98DDBT0\\MYSQLSERVER;database=PersianExpressDB;encrypt=true;trustServerCertificate=true", "sa", "hmnxt");
        statement= connection.prepareStatement("select AccountNumber , AccountID from BankAccounts where CustomerID=?");
        statement.setInt(1 , HelloController.userID);
        ResultSet resultSet =statement.executeQuery();
        while (resultSet.next()){
            BankAccount.add(resultSet.getNString("AccountNumber"));
            BankAccountID.add(resultSet.getInt("AccountID"));
        }
        SelectedAccount.getItems().addAll(BankAccount);
        SelectedAccount.setValue(BankAccount.get(0));

        AlternativeAccount.getItems().addAll(BankAccount);
        AlternativeAccount.setValue(BankAccount.get(0));
    }
    public void onSubmitClicked(ActionEvent event) throws SQLException, IOException {
        PreparedStatement statement1 = connection.prepareStatement("SELECT AccountID FROM DeleteAccountREQ WHERE CustomerID = ?");
        statement1.setInt(1,HelloController.userID);
        ResultSet resultSet1 = statement1.executeQuery();
        ArrayList<String> illegalAccs = new ArrayList<>();
        while (resultSet1.next()){
            GharzolH acc = new GharzolH(resultSet1.getInt("AccountID"),HelloController.userID);
            illegalAccs.add(acc.getAccountNumber(connection));
        }
        if (illegalAccs.contains(SelectedAccount.getValue())||illegalAccs.contains(AlternativeAccount.getValue())){
            errorText.setText("قبلا برای این حساب ها درخواست ثبت شده است.");
        } else if (Objects.equals(SelectedAccount.getValue(), AlternativeAccount.getValue())){
            errorText.setText("شماره حساب مبدا و حساب جایگزین باید متفاوت باشند.");
        }
        else if(BankAccount.size()==1){
            errorText.setText("شما قادر به بستن حساب خود نمی باشید.");
        } else if (!Objects.equals(SelectedAccount.getValue(), AlternativeAccount.getValue())) {
            errorText.setText(" ");
            int index = BankAccount.indexOf(SelectedAccount.getValue());
            statement=connection.prepareStatement("insert into DeleteAccountREQ(CustomerID,AccountID,SubstituteAccount,RequestDate,RequestStatus) values (? , ? , ? , ? , ?)");
            statement.setInt(1,HelloController.userID);
            statement.setInt(2,BankAccountID.get(index));
            statement.setNString(3,AlternativeAccount.getValue());
            statement.setDate(4,currentDate);
            statement.setBoolean(5,false);
            int resultSet = statement.executeUpdate();
            Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();
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
