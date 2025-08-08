module org.example.powerpark {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires com.dlsc.formsfx;
    requires java.sql;

    // Open controller package for FXML and allow Main to be constructed
    opens org.example.powerpark.controller to javafx.fxml, javafx.graphics;
    exports org.example.powerpark.controller;
}
