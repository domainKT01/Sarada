package com.solproe.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML Button createConfig;
    @FXML Button dashboardButton;
    @FXML BorderPane borderPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dashboardButton.setOnMousePressed(_ -> {
            try {
                BorderPane borderPaneConf = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/create-file-config.fxml")));
                this.borderPane.setCenter(borderPaneConf);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.createConfig.setOnMousePressed(_ -> {
            System.out.println("pressed config");
        });
    }
}
