package com.solproe.ui.controllers;

import com.solproe.ui.viewModels.ConfigFileViewModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MonthlyConfigFileController implements Initializable {

    public TextField orangeThresholdTemperature;
    public TextField redThresholdTemperature;
    public TextField orangeThresholdPrecipitation;
    public TextField redThresholdPrecipitation;
    public Label percentHead;
    public Label gradeHead;
    public TextField januaryDataGrade;
    public TextField januaryDataPercent;
    public TextField februaryDataGrade;
    public TextField februaryDataPercent;
    public TextField marchDataGrade;
    public TextField marchDataPercent;
    public TextField aprilDataGrade;
    public TextField aprilDataPercent;
    public TextField mayDataGrade;
    public TextField mayDataPercent;
    public TextField juneDataGrade;
    public TextField juneDataPercent;
    public TextField julyDataGrade;
    public TextField julyDataPercent;
    public TextField augustDataGrade;
    public TextField augustDataPercent;
    public TextField septemberDataGrade;
    public TextField septemberDataPercent;
    public TextField octoberDataGrade;
    public TextField octoberDataPercent;
    public TextField novemberDataGrade;
    public TextField novemberDataPercent;
    public TextField decemberDataGrade;
    public TextField decemberDataPercent;
    public Button buttonSave;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConfigFileViewModel viewModel = new ConfigFileViewModel();
        this.buttonSave.setOnMouseClicked(_ -> {
            try {
                double[] list = {
                        Double.parseDouble(orangeThresholdTemperature.getText()),
                        Double.parseDouble(redThresholdTemperature.getText()),
                        Double.parseDouble(orangeThresholdPrecipitation.getText()),
                        Double.parseDouble(redThresholdPrecipitation.getText()),
                        Double.parseDouble(januaryDataGrade.getText()),
                        Double.parseDouble(januaryDataPercent.getText()),
                        Double.parseDouble(februaryDataGrade.getText()),
                        Double.parseDouble(februaryDataPercent.getText()),
                        Double.parseDouble(marchDataGrade.getText()),
                        Double.parseDouble(marchDataPercent.getText()),
                        Double.parseDouble(aprilDataGrade.getText()),
                        Double.parseDouble(aprilDataPercent.getText()),
                        Double.parseDouble(mayDataGrade.getText()),
                        Double.parseDouble(mayDataPercent.getText()),
                        Double.parseDouble(juneDataGrade.getText()),
                        Double.parseDouble(juneDataPercent.getText()),
                        Double.parseDouble(julyDataGrade.getText()),
                        Double.parseDouble(julyDataPercent.getText()),
                        Double.parseDouble(augustDataGrade.getText()),
                        Double.parseDouble(augustDataPercent.getText()),
                        Double.parseDouble(septemberDataGrade.getText()),
                        Double.parseDouble(septemberDataPercent.getText()),
                        Double.parseDouble(octoberDataGrade.getText()),
                        Double.parseDouble(octoberDataPercent.getText()),
                        Double.parseDouble(novemberDataGrade.getText()),
                        Double.parseDouble(novemberDataPercent.getText()),
                        Double.parseDouble(decemberDataGrade.getText()),
                        Double.parseDouble(decemberDataPercent.getText())
                };

                if (validateTexFields(list)) {
                    ConfigFileViewModel configFileViewModel = new ConfigFileViewModel();
                    configFileViewModel.createConfigFileMonthly(list);
                }
            }
            catch (Exception e) {
                System.out.println("exc: " + e.getMessage());
            }
        });
    }

    private boolean validateTexFields(double[] list) {
        for (double textField : list) {
            try {

            }
            catch (NullPointerException n) {
                return false;
            }
        }
        return true;
    }
}
