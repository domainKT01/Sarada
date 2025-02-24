package com.solproe.ui.controllers;

import com.solproe.ui.viewModels.ConfigFileViewModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FormController implements Initializable {

    public TextField forestFireThresholdOrange = null;
    public TextField forestFireThresholdRed = null;
    public TextField precipitationThresholdOrange = null;
    public TextField precipitationThresholdRed = null;
    public TextField windThresholdOrange = null;
    public TextField windThresholdRed = null;
    public TextField precipitationRainPercentOrange = null;
    public TextField precipitationRainPercentRed = null;
    public TextField ceraunicosThresholdRed = null;
    public TextField projectName = null;
    public TextField stateName = null;
    public TextField cityName = null;
    public TextField idProject = null;
    public TextField sciBoss = null;
    public TextField sciBossContact = null;
    public TextField auxiliarSciBoss = null;
    public TextField auxiliarSciBossContact = null;
    public Button saveButton = null;
    public Button updateButton = null;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConfigFileViewModel configFileViewModel = new ConfigFileViewModel();
        this.saveButton.setOnMouseClicked(_ -> {
            TextField[] listFields = {
                    forestFireThresholdOrange,
                    forestFireThresholdRed,
                    precipitationThresholdOrange,
                    precipitationThresholdRed,
                    windThresholdOrange,
                    windThresholdRed,
                    precipitationRainPercentOrange,
                    precipitationRainPercentRed,
                    ceraunicosThresholdRed,
                    projectName,
                    stateName,
                    cityName,
                    idProject,
                    sciBoss,
                    sciBossContact,
                    auxiliarSciBoss,
                    auxiliarSciBossContact
            };

            if (validateTexFields(listFields)) {
                configFileViewModel.createConfigFileThreshold(
                        Double.parseDouble(this.forestFireThresholdOrange.getText()),
                        Double.parseDouble(this.forestFireThresholdRed.getText()),
                        Double.parseDouble(this.precipitationThresholdOrange.getText()),
                        Double.parseDouble(this.precipitationThresholdRed.getText()),
                        Double.parseDouble(this.windThresholdOrange.getText()),
                        Double.parseDouble(this.windThresholdRed.getText()),
                        Double.parseDouble(this.precipitationRainPercentOrange.getText()),
                        Double.parseDouble(this.precipitationRainPercentRed.getText()),
                        Double.parseDouble(this.ceraunicosThresholdRed.getText()),
                        this.ceraunicosThresholdRed.getText(),
                        this.stateName.getText(),
                        this.cityName.getText(),
                        this.idProject.getText(),
                        this.sciBoss.getText(),
                        Long.parseLong(this.sciBossContact.getText()),
                        this.auxiliarSciBoss.getText(),
                        Long.parseLong(this.auxiliarSciBossContact.getText())
                );
            }
        });
    }

    private boolean validateTexFields(TextField[] list) {
        for (TextField textField : list) {
            try {
                assert textField.getText() != null || !Objects.equals(textField.getText(), "");
            }
            catch (NullPointerException n) {
                return false;
            }
        }
        return true;
    }
}
