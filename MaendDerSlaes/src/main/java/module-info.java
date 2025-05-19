module com.example.maendderslaes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires javafx.media;
    requires java.desktop;
    requires org.xerial.sqlitejdbc;

    opens com.example.maendderslaes to javafx.fxml;
    exports com.example.maendderslaes;
}