package com.solproe.ui.controllers;

import com.solproe.business.usecase.GenerateReportUseCase;
import com.solproe.ui.viewModels.GenerateReportViewModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    public Button buttonGenerate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.buttonGenerate.setOnMouseClicked(_ -> {
            GenerateReportViewModel viewModel = new GenerateReportViewModel();
            viewModel.generateReport();
        });
    }
}
