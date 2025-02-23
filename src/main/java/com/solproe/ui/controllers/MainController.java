package com.solproe.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML Button createCodeList;
    @FXML Button createConfig;
    @FXML Button dashboardButton;
    @FXML BorderPane borderPane;
    private Button lastClicked;
    private BorderPane borderPaneConf = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("loading dashboard");
            this.borderPaneConf = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/dashboard.fxml")));
            this.borderPane.setCenter(this.borderPaneConf);
        } catch (IOException e) {
            System.out.println("error loading dashboard");
            throw new RuntimeException(e);
        }

        this.createConfig.setOnMousePressed(_ -> {
            try {
                this.setCenter("/views/config/create-file-config.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (this.lastClicked != null) {
                this.lastClicked.setFont(new Font("System", 13.0F));
            }
            this.lastClicked = this.createConfig;
            this.lastClicked.setFont(new Font("Cursive", 18F));
        });

        this.createCodeList.setOnMouseClicked(_ -> {
           try {
               this.setCenter("/views/config/create-form-code.fxml");
           }
           catch (IOException e) {
               System.out.println(e.getMessage());
               throw new RuntimeException();
           }

            if (this.lastClicked != null) {
                this.lastClicked.setFont(new Font("System", 13.0F));
            }
            this.lastClicked = this.createCodeList;
            this.lastClicked.setFont(new Font("Cursive", 18F));
        });

        this.dashboardButton.setOnMouseClicked(_ -> {
            try {
                this.setCenter("/views/dashboard.fxml");
            }
            catch (IOException e) {
                throw new RuntimeException();
            }

            if (this.lastClicked != null) {
                this.lastClicked.setFont(new Font("System", 13.0F));
            }
            this.lastClicked = this.dashboardButton;
            this.lastClicked.setFont(new Font("Cursive", 18F));
        });
    }

    private void setCenter(String viewPath) throws IOException {
        this.borderPaneConf = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(viewPath)));
        this.borderPane.setCenter(this.borderPaneConf);
    }
}
