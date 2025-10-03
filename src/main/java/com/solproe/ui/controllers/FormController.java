package com.solproe.ui.controllers;

import com.solproe.business.dto.ThresholdInputModel;
import com.solproe.business.repository.ErrorCallback;
import com.solproe.business.repository.SuccessCallback;
import com.solproe.ui.viewModels.ConfigFileViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.apache.poi.ss.formula.functions.T;

import java.net.URL;
import java.util.ResourceBundle;

public class FormController implements Initializable {

    public Button saveButton;

    public Button updateButton;
    @FXML
    private TextField projectName;
    @FXML
    private TextField idProject;
    @FXML
    private TextField cityName;
    @FXML
    private TextField stateName;
    @FXML
    private TextField sciBoss;
    @FXML
    private TextField sciBossContact;
    @FXML
    private TextField auxiliarSciBoss;
    @FXML
    private TextField auxiliarSciBossContact;
    @FXML
    private TextField forestFireOrange;
    @FXML
    private TextField forestFireRed;
    @FXML
    private TextField precipitationOrange;
    @FXML
    private TextField precipitationRed;
    @FXML
    private TextField windOrange;
    @FXML
    private TextField windRed;
    @FXML
    private TextField percentRainOrange;
    @FXML
    private TextField percentRainRed;
    @FXML
    private TextField ceraunicosRed;

    private ConfigFileViewModel viewModel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.viewModel = new ConfigFileViewModel();
        this.saveButton.setOnMouseClicked(_ -> saveConfig());
    }

    private void saveConfig() {
        try {
            // Validar entradas
            ThresholdInputModel input = new ThresholdInputModel();
            input.setProjectName(projectName.getText());
            input.setIdProject(idProject.getText());
            input.setCityName(cityName.getText());
            input.setStateName(stateName.getText());
            input.setSciBoss(sciBoss.getText());
            input.setSciBossContact(parseLong(sciBossContact.getText()));
            input.setAuxiliarSciBoss(auxiliarSciBoss.getText());
            input.setAuxiliarSciBossContact(parseLong(auxiliarSciBossContact.getText()));
            input.setForestFireThresholdOrange(parseDouble(forestFireOrange.getText()));
            input.setForestFireThresholdRed(parseDouble(forestFireRed.getText()));
            input.setPrecipitationThresholdOrange(parseDouble(precipitationOrange.getText()));
            input.setPrecipitationThresholdRed(parseDouble(precipitationRed.getText()));
            input.setWindThresholdOrange(parseDouble(windOrange.getText()));
            input.setWindThresholdRed(parseDouble(windRed.getText()));
            input.setPrecipitationRainPercentOrange(parseDouble(percentRainOrange.getText()));
            input.setPrecipitationRainPercentRed(parseDouble(percentRainRed.getText()));
            input.setCeraunicosThresholdRed(parseDouble(ceraunicosRed.getText()));

            // Llamada al ViewModel
            SuccessCallback successCallback = () -> {
                System.out.println("success funcionando #######");
                Platform.runLater(() -> {
                    showAlert(AlertType.INFORMATION, "Configuración guardada",
                            "El archivo de configuración se ha guardado correctamente.");
                });
            };

            ErrorCallback errorCallback = _ -> {
                System.out.println("error funcionando #######");
                Platform.runLater(() -> {
                    showAlert(AlertType.ERROR, "Error",
                            "Hubo un problema al guardar el archivo de configuración.");
                });
            };
            viewModel.createConfigFileThresholdAsync(successCallback, errorCallback, input);
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Error al guardar configuración: " + e.getMessage());
        }
    }

    // Método para parsear Strings a Long y manejar posibles errores
        private long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error de formato", "El valor debe ser un número válido.");
            throw e;
        }
    }

    // Método para parsear Strings a Double y manejar posibles errores
    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error de formato", "El valor debe ser un número válido.");
            throw e;
        }
    }

    // Método para mostrar alertas
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
