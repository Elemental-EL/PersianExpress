module org.example.persianexpress {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.persianexpress to javafx.fxml;
    exports org.example.persianexpress;
    exports org.example.persianexpress.Controllers.Customers;
    opens org.example.persianexpress.Controllers.Customers to javafx.fxml;
}