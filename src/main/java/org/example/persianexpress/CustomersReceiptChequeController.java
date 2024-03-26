package org.example.persianexpress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.persianexpress.Objects.GharzolH;
import org.example.persianexpress.Objects.StockCheque;
import org.example.persianexpress.Objects.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class CustomersReceiptChequeController {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField firstName , nationalCode , chequeSerialNumber, chequeAmount , lastName;
    @FXML
    private DatePicker chequeDate;
    @FXML
    private ChoiceBox<String> selectedAccount;
    @FXML
    private Text errorText;
    @FXML
    private Button submitBtn;
    private User user;
    private Connection connection;

    public void initialize() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-IQ6LNQ5;database=PersianExpressDB;encrypt=true;trustServerCertificate=true" , "PEDB" , "pedb1234");
        user = User.createUserObj(connection,HelloController.userID);
        firstName.setText(user.getfName());
        lastName.setText(user.getLastname());
        nationalCode.setText(user.getNationalCode());
        String[] accs = GharzolH.getAccNumsForReceipt(connection);
        selectedAccount.getItems().addAll(accs);
        selectedAccount.setValue(accs[0]);
        if (accs.length==1){
            errorText.setText("*شما حساب معتبری برای وصول چک ندارید.");
            submitBtn.setDisable(true);
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

    public void onSumbitClicked(ActionEvent event) throws SQLException, IOException {
        ResultSet resultSet = StockCheque.getUserCheques(connection);
        boolean flag = false;
        if (chequeSerialNumber.getText().trim().isEmpty() || chequeAmount.getText().trim().isEmpty() || chequeDate.getValue() == null || selectedAccount.getSelectionModel().getSelectedItem().equals("انتخاب کنید")){
            errorText.setText("*پر کردن تمامی فیلد ها الزامی است.");
        } else {
            while (!flag && resultSet.next()){
                if (chequeSerialNumber.getText().equals(String.valueOf(resultSet.getLong("ChequeSerialNum")))&&chequeAmount.getText().equals(String.valueOf(resultSet.getLong("ChequeAmount")))&&chequeDate.getValue().equals(resultSet.getDate("ChequeDate").toLocalDate())){
                    flag = true;
                }
            }
            if (flag){
                PreparedStatement statement = connection.prepareStatement("INSERT INTO ReceiptChequeREQ (CustomerID,AccountID,RequestDate,ChequeSerialNum,ChequeDate,ChequeAmount,ReceiptChequeStatus) VALUES (?,?,?,?,?,?,?)");
                statement.setInt(1,HelloController.userID);
                statement.setInt(2,GharzolH.getAccountUID(selectedAccount.getSelectionModel().getSelectedItem(),connection));
                statement.setDate(3, Date.valueOf(LocalDate.now()));
                statement.setLong(4, Long.parseLong(chequeSerialNumber.getText()));
                statement.setDate(5,resultSet.getDate("ChequeDate"));
                statement.setLong(6,resultSet.getLong("ChequeAmount"));
                statement.setBoolean(7,false);
                int resN = statement.executeUpdate();
                Parent root = FXMLLoader.load(getClass().getResource("Pages/Customers/CustomersPanel.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
            } else {
                errorText.setText("اطلاعات وارده معتبر نمی باشند.");
            }
        }
    }

}
