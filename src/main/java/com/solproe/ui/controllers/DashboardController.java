package com.solproe.ui.controllers;

import com.solproe.business.dto.DashboardDto;
import com.solproe.ui.components.StoreValues;
import com.solproe.ui.viewModels.ConfigFileViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.util.StringConverter;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML public TextField tokenWhatsapp;
    @FXML public Button buttonSave;
    @FXML public Spinner hourSpinner;
    @FXML public Spinner minuteSpinner;
    @FXML public Button buttonDirectory;
    @FXML public String pathDirectory;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConfigFileViewModel viewModel = new ConfigFileViewModel();

        SpinnerValueFactory<Integer> hourFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12);

        hourFactory.setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer value) {
                if (value == null) return "";
                return String.format("%02d", value);
            }

            @Override
            public Integer fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return 0;
                }
                try {
                    int value = Integer.parseInt(string.trim());
                    if (value >= 0 && value <= 23) {
                        return value;
                    } else {
                        return 0;
                    }
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        });

        hourSpinner.setValueFactory(hourFactory);
        hourSpinner.setEditable(true);
        hourSpinner.getEditor().setPromptText("HH");

        // Configurar spinner de minutos
        SpinnerValueFactory<Integer> minuteFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);

        minuteFactory.setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer value) {
                if (value == null) return "";
                return String.format("%02d", value);
            }

            @Override
            public Integer fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return 0;
                }
                try {
                    int value = Integer.parseInt(string.trim());
                    if (value >= 0 && value <= 59) {
                        return value;
                    } else {
                        return 0;
                    }
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        });

        minuteSpinner.setValueFactory(minuteFactory);
        minuteSpinner.setEditable(true);
        minuteSpinner.getEditor().setPromptText("mm");

        this.buttonDirectory.setOnAction(_ -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File initialDirectory = new File(System.getProperty("user.home")); // Example: User's home directory
            directoryChooser.setInitialDirectory(initialDirectory);
            directoryChooser.setTitle("Select a Directory");
            File selectedDirectory = directoryChooser.showDialog(StoreValues.getStage());
            try {
                pathDirectory = selectedDirectory.getAbsolutePath();
            }
            catch (NullPointerException e) {
                pathDirectory = null;
            }
            System.out.println("path directory: " + pathDirectory);
        });

        this.buttonSave.setOnAction(_ -> {
                    if (((int) hourSpinner.getValue() > 0 && (int) hourSpinner.getValue() <= 23) &&
                            ((int) minuteSpinner.getValue() > 0 && (int) hourSpinner.getValue() <= 59)) {
                        viewModel.createConfigDash(new DashboardDto(tokenWhatsapp.getText(),
                                hourSpinner.getValue().toString(),
                                minuteSpinner.getValue().toString(),
                                pathDirectory
                                )
                        );
                        showAlert(Alert.AlertType.CONFIRMATION, "Confirmado", "Se guardó la configuración exitosamente");
                    }
                    else {
                        showAlert(Alert.AlertType.WARNING, "Error", "No se pudo guardar la configuración");
                    }
                }
        );
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
