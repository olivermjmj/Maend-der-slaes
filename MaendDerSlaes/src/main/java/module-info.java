module com.example.maendderslaes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.maendderslaes to javafx.fxml;
    exports com.example.maendderslaes;
}