package com.solproe.ui.controllers;

import com.solproe.ui.viewModels.ConfigFileViewModel;
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

    public Button challengesButton;
    @FXML Button createCodeList;
    @FXML Button createConfig;
    @FXML Button dashboardButton;
    @FXML BorderPane borderPane;
    private Button lastClicked;
    private BorderPane borderPaneConf = null;
    private FormController formController;
    private ConfigFileViewModel configFileViewModel = new ConfigFileViewModel();


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
                this.formController = this.setCenter("/views/config/create-file-config.fxml").getController();
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
                this.setCenter("/views/dashboard.fxml").getController();
            }
            catch (IOException e) {
                System.out.println("error load");
                throw new RuntimeException();
            }

            if (this.lastClicked != null) {
                this.lastClicked.setFont(new Font("System", 13.0F));
            }
            this.lastClicked = this.dashboardButton;
            this.lastClicked.setFont(new Font("Cursive", 18F));
        });

        this.challengesButton.setOnMousePressed(_ -> {
            try {
                this.setCenter("/views/report/report-generate.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (this.lastClicked != null) {
                this.lastClicked.setFont(new Font("System", 13.0F));
            }
            this.lastClicked = this.challengesButton;
            this.lastClicked.setFont(new Font("Cursive", 18F));
            System.out.println("report");
        });
    }

    private FXMLLoader setCenter(String viewPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        this.borderPaneConf = loader.load();
        this.borderPane.setCenter(this.borderPaneConf);
        return loader;
    }
}
