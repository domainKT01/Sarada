package com.solproe.ui.controllers;

import com.solproe.business.dto.MonthlyData;
import com.solproe.business.dto.MonthlyThresholdInputModel;
import com.solproe.ui.viewModels.ConfigFileViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MonthlyConfigFileController implements Initializable {

    @FXML private TextField orangeThresholdTemperature;
    @FXML private TextField redThresholdTemperature;
    @FXML private TextField orangeThresholdPrecipitation;
    @FXML private TextField redThresholdPrecipitation;

    @FXML private TextField januaryDataGrade, januaryDataPercent;
    @FXML private TextField februaryDataGrade, februaryDataPercent;
    @FXML private TextField marchDataGrade, marchDataPercent;
    @FXML private TextField aprilDataGrade, aprilDataPercent;
    @FXML private TextField mayDataGrade, mayDataPercent;
    @FXML private TextField juneDataGrade, juneDataPercent;
    @FXML private TextField julyDataGrade, julyDataPercent;
    @FXML private TextField augustDataGrade, augustDataPercent;
    @FXML private TextField septemberDataGrade, septemberDataPercent;
    @FXML private TextField octoberDataGrade, octoberDataPercent;
    @FXML private TextField novemberDataGrade, novemberDataPercent;
    @FXML private TextField decemberDataGrade, decemberDataPercent;

    @FXML private Button buttonSave;

    private ConfigFileViewModel viewModel;


    private record MonthlyDataPair(String month, TextField grade, TextField percent) {
        boolean isFirstSemester() {
            return switch (month.toLowerCase()) {
                case "january", "february", "march", "april", "may", "june" -> true;
                default -> false;
            };
        }

        boolean isFilled() {
            return !grade.getText().isBlank() && !percent.getText().isBlank();
        }

        boolean isComplete() {
            return !grade.getText().isBlank() && !percent.getText().isBlank();
        }

        double[] toArray() {
            return new double[] {
                    Double.parseDouble(grade.getText()),
                    Double.parseDouble(percent.getText())
            };
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.viewModel = new ConfigFileViewModel();
        buttonSave.setOnMouseClicked(e -> saveConfig());
    }

    private void saveConfig() {
        try {
            MonthlyThresholdInputModel inputModel = new MonthlyThresholdInputModel();

            // Validar y agregar los umbrales
            inputModel.setOrangeTemperatureThreshold(Double.parseDouble(orangeThresholdTemperature.getText()));
            inputModel.setRedTemperatureThreshold(Double.parseDouble(redThresholdTemperature.getText()));
            inputModel.setOrangePrecipitationThreshold(Double.parseDouble(orangeThresholdPrecipitation.getText()));
            inputModel.setRedPrecipitationThreshold(Double.parseDouble(redThresholdPrecipitation.getText()));

            // Obtener pares de datos mensuales
            List<MonthlyDataPair> allMonths = getMonthlyDataPairs();
            List<MonthlyDataPair> filled = allMonths.stream().filter(MonthlyDataPair::isFilled).toList();

            if (filled.isEmpty()) {
                showAlert(AlertType.WARNING, "Advertencia", "No se ingresaron datos de ningún mes.");
                return;
            }

            boolean firstSemester = filled.getFirst().isFirstSemester();

            for (MonthlyDataPair pair : filled) {
                if (!pair.isComplete()) {
                    showAlert(AlertType.WARNING, "Advertencia", "Faltan datos para el mes: " + pair.month);
                    return;
                }
                if (pair.isFirstSemester() != firstSemester) {
                    showAlert(AlertType.WARNING, "Advertencia", "No se pueden ingresar datos de ambos semestres.");
                    return;
                }

                MonthlyData monthlyData =
                        new MonthlyData(pair.month,
                                pair.grade,
                                pair.percent);

                inputModel.getMonthlyData().add(monthlyData);
            }

            boolean success = viewModel.createConfigFileMonthly(inputModel);
            if (success) {
                showAlert(AlertType.INFORMATION, "Éxito", "Archivo mensual creado exitosamente.");
            } else {
                showAlert(AlertType.ERROR, "Error", "Hubo un problema al crear el archivo.");
            }

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Error al guardar configuración mensual: " + e.getMessage());
        }
    }


    // Método para validar los umbrales antes de agregar los valores
    private void validateAndAddThresholdData(List<Double> data) throws NumberFormatException {
        try {
            data.add(Double.parseDouble(orangeThresholdTemperature.getText()));
            data.add(Double.parseDouble(redThresholdTemperature.getText()));
            data.add(Double.parseDouble(orangeThresholdPrecipitation.getText()));
            data.add(Double.parseDouble(redThresholdPrecipitation.getText()));
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Los umbrales deben ser valores numéricos válidos.");
            throw e;  // Re-lanzar para que no continúe con datos incorrectos
        }
    }

    // Método para obtener los pares de datos mensuales
    private List<MonthlyDataPair> getMonthlyDataPairs() {
        return List.of(
                new MonthlyDataPair("Enero", januaryDataGrade, januaryDataPercent),
                new MonthlyDataPair("Febrero", februaryDataGrade, februaryDataPercent),
                new MonthlyDataPair("Marzo", marchDataGrade, marchDataPercent),
                new MonthlyDataPair("Abril", aprilDataGrade, aprilDataPercent),
                new MonthlyDataPair("Mayo", mayDataGrade, mayDataPercent),
                new MonthlyDataPair("Junio", juneDataGrade, juneDataPercent),
                new MonthlyDataPair("Julio", julyDataGrade, julyDataPercent),
                new MonthlyDataPair("Agosto", augustDataGrade, augustDataPercent),
                new MonthlyDataPair("Septiembre", septemberDataGrade, septemberDataPercent),
                new MonthlyDataPair("Octubre", octoberDataGrade, octoberDataPercent),
                new MonthlyDataPair("Noviembre", novemberDataGrade, novemberDataPercent),
                new MonthlyDataPair("Diciembre", decemberDataGrade, decemberDataPercent)
        );
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
