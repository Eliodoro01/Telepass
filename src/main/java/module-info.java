module com.example.telepass {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.telepass to javafx.fxml;
    exports com.example.telepass;
}