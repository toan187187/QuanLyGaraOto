module com.example.doan123 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires java.desktop;
    requires javafx.graphics;
    requires itextpdf;

    opens com.example.doan123.Controller to javafx.fxml;

    opens com.example.doan123 to javafx.fxml;
    exports com.example.doan123;

    opens com.example.doan123.Model to javafx.base;
}