package com.solproe.ui.controllers;

import com.solproe.ui.viewModels.ConfigFileViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateFormCode implements Initializable {

    public HBox horizontalBottomContainer;
    @FXML HBox view;
    @FXML HBox horizontalContainerSlider;
    @FXML AnchorPane buttonSave;
    @FXML BorderPane createListCode;
    private ButtonSaveComponent buttonSaveComponent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader viewLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/views/config/listCode/view.fxml")));
            this.view = viewLoader.load();
            FXMLLoader viewButton = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/views/components/button-save.fxml")));
            this.buttonSave = viewButton.load();
            this.horizontalContainerSlider.getChildren().addAll(this.view);
            this.horizontalBottomContainer.getChildren().addAll(this.buttonSave);
            this.buttonSaveComponent = viewButton.getController();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
