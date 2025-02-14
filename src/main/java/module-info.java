module com.solproe {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.media;
    requires javafx.web;
    requires javafx.swing;

    exports com.solproe;
    exports com.solproe.ui.controllers;

    opens com.solproe.ui.controllers;
}