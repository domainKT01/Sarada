package com.solproe.ui.controllers;

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
    private HBox view;
    @FXML HBox horizontalContainerSlider;
    private int actualView = 0;
    @FXML private AnchorPane buttonSave;
    @FXML BorderPane createListCode;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/config/listCode/view.fxml")));
            this.buttonSave = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/components/button-save.fxml")));
            this.horizontalContainerSlider.getChildren().addAll(this.view);
            this.horizontalBottomContainer.getChildren().addAll(this.buttonSave);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void getData() {

    }
}
