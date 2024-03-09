module org.example.persianexpress {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.persianexpress to javafx.fxml;
    exports org.example.persianexpress;
}