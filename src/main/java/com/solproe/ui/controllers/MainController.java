package com.solproe.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML Button createConfig;
    @FXML Button dashboardButton;
    @FXML BorderPane borderPane;
    @FXML Button createList;

    private Button lastClicked;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("hi");
        this.createConfig.setOnMouseClicked(_ -> {
            if (this.lastClicked != null) {
                this.lastClicked.setFont(new Font("System", 13.0F));
            }
            this.lastClicked = this.createConfig;
            this.lastClicked.setFont(new Font("Cursive", 18F));
        });

        this.createList.setOnMouseClicked(_ -> {
            if (this.lastClicked != null) {
                this.lastClicked.setFont(new Font("System", 13.0F));
            }
            this.lastClicked = this.createList;
            this.lastClicked.setFont(new Font("Cursive", 18F));
        });

        this.createConfig.setOnMousePressed(_ -> {
            BorderPane borderPaneConf = null;
            try {
                borderPaneConf = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/config/create-file-config.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.borderPane.setCenter(borderPaneConf);
        });
    }

}
