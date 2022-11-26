module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires java.net.http;
    requires json.simple;
    requires org.apache.poi.ooxml;
    requires itextpdf;
    requires twilio;
    requires transitive java.desktop;

    opens gui to javafx.fxml;
    exports gui;
    exports dto;
    opens dto to javafx.fxml;
}