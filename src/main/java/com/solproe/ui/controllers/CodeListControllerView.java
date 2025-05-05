package com.solproe.ui.controllers;

import com.solproe.business.dto.ListCodeDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CodeListControllerView implements Initializable {

    @FXML
    public HBox horizontalContainerSlider;
    @FXML
    public HBox horizontalBottomContainer;
    @FXML
    public Button buttonSave;
    @FXML
    private TextField clearSky;
    @FXML
    private TextField mainlyClear, partlyCloud, overcast;
    @FXML
    private TextField fog, depositingRimeFog;
    @FXML
    private TextField lightDrizzle, moderateDrizzle, denseIntensityDrizzle;
    @FXML
    private TextField lightFreezingDrizzle, denseIntensityFreezingDrizzle;
    @FXML
    private TextField slightRain, moderateRain, heavyIntensityRain;
    @FXML
    private TextField lightFreezingRain, heavyIntensityFreezingRain;
    @FXML
    private TextField slightSnowFall, moderateSnowfall, heavyIntensitySnowFall;
    @FXML
    private TextField snowGrains;
    @FXML
    private TextField slightRainShower, moderateRainShower, violentRainShower;
    @FXML
    private TextField slightSnowShower, heavySnowShower;
    @FXML
    private TextField thunderstorm;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.buttonSave.setOnMouseClicked(_ -> {
            ListCodeDTO listCodeDTO = ListCodeDTO.builder()
                    .clearSky(Integer.parseInt(this.clearSky.getText()))
                    .build();
        });
    }
}
