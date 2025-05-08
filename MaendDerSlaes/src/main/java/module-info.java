module com.example.maendderslaes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.media;

    opens com.example.maendderslaes to javafx.fxml;
    exports com.example.maendderslaes;
}