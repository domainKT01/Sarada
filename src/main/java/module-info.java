module com.solproe {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.media;
    requires javafx.web;
    requires javafx.swing;
    requires com.google.gson;
    requires okhttp3;
    requires javax.inject;
    requires dagger;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.jetbrains.annotations;
    requires org.apache.logging.log4j;
    requires quartz;
    requires org.slf4j;
    requires mchange.commons.java;
    requires kotlin.stdlib;
    requires org.apache.commons.io;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires java.net.http;

    exports com.solproe;
    exports com.solproe.ui.controllers;

    opens com.solproe.ui.controllers;
}