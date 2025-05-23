package com.solproe.ui.controllers;

import com.solproe.business.dto.ListCodeDTO;
import com.solproe.ui.viewModels.ConfigFileViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private TextField lightFreezingDrizzle, heavyIntensityFreezingDrizzle;
    @FXML
    private TextField slightRain, moderateRain, heavyIntensityRain;
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
                    .mainlyClear(Integer.parseInt(this.mainlyClear.getText()))
                    .partlyCloudy(Integer.parseInt(this.partlyCloud.getText()))
                    .overcast(Integer.parseInt(this.overcast.getText()))
                    .fog(Integer.parseInt(this.fog.getText()))
                    .depositingRimeFog(Integer.parseInt(this.depositingRimeFog.getText()))
                    .lightDrizzle(Integer.parseInt(this.lightDrizzle.getText()))
                    .moderateDrizzle(Integer.parseInt(this.moderateDrizzle.getText()))
                    .denseIntensityDrizzle(Integer.parseInt(this.denseIntensityDrizzle.getText()))
                    .lightFreezingDrizzle(Integer.parseInt(this.lightFreezingDrizzle.getText()))
                    .heavyIntensityFreezingDrizzle(Integer.parseInt(this.heavyIntensityFreezingDrizzle.getText()))
                    .slightRain(Integer.parseInt(this.slightRain.getText()))
                    .moderateRain(Integer.parseInt(this.moderateRain.getText()))
                    .heavyIntensityRain(Integer.parseInt(this.heavyIntensityRain.getText()))
                    .slightSnowFall(Integer.parseInt(this.slightSnowFall.getText()))
                    .moderateSnowfall(Integer.parseInt(this.moderateSnowfall.getText()))
                    .heavyIntensitySnowFall(Integer.parseInt(this.heavyIntensitySnowFall.getText()))
                    .snowGrains(Integer.parseInt(this.snowGrains.getText()))
                    .slightRainShower(Integer.parseInt(this.slightRainShower.getText()))
                    .moderateRainShower(Integer.parseInt(this.moderateRainShower.getText()))
                    .violentRainShower(Integer.parseInt(this.violentRainShower.getText()))
                    .slightSnowShower(Integer.parseInt(this.slightSnowShower.getText()))
                    .heavySnowShower(Integer.parseInt(this.heavySnowShower.getText()))
                    .thunderstorm(Integer.parseInt(this.thunderstorm.getText()))
                    .build();

            ConfigFileViewModel viewModel = new ConfigFileViewModel();
            boolean bool = viewModel.createConfigCodesFile(listCodeDTO);

            if (bool) {
                showAlert(Alert.AlertType.INFORMATION, "Configuración guardada", "El archivo de configuración se ha guardado correctamente.");
            }
            else {
                showAlert(Alert.AlertType.ERROR, "Error", "Hubo un problema al guardar el archivo de configuración.");
            }
        });
    }

    // Método para mostrar alertas
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
