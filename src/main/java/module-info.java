module org.example.persianexpress {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.persianexpress to javafx.fxml;
    exports org.example.persianexpress;
    exports org.example.persianexpress.Objects;
    opens org.example.persianexpress.Objects to javafx.fxml;
}