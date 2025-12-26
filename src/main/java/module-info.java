module OAnQuanMaven {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.base;
    requires javafx.graphics;

    opens com.code to javafx.graphics, javafx.fxml;
    opens com.code.controller to javafx.fxml;
    opens css to javafx.graphics, javafx.fxml;
    opens image to javafx.graphics,javafx.fxml;
    // opens com.code.view to javafx.fxml;
    exports com.code;
}