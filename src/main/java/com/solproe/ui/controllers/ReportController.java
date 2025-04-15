package com.solproe.ui.controllers;

import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.repository.ExcelFileGenerator;
import com.solproe.business.repository.ReadConfigFile;
import com.solproe.business.repository.ReportState;
import com.solproe.business.usecase.GenerateReportUseCase;
import com.solproe.service.APIs.ApiCommandInvoker;
import com.solproe.service.APIs.ApiService;
import com.solproe.service.APIs.GetRequestApi;
import com.solproe.service.config.ReadJsonConfigFile;
import com.solproe.service.excel.ExcelService;
import com.solproe.service.excel.ReportExcelGenerator;
import com.solproe.ui.viewModels.GenerateReportViewModel;
import com.solproe.util.ThreadUtil;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    public Button buttonGenerate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.buttonGenerate.setOnMouseClicked(_ -> {
            // Wiring manual (o automatizado con inyección más adelante)
            ApiCommandInvoker apiCommandInvoker = new ApiCommandInvoker();
            GenerateReportUseCase useCase = new GenerateReportUseCase();

            ApiService apiService = new ApiService(apiCommandInvoker, useCase);
            apiCommandInvoker.setRequestInterface(apiService);

            ApiCommandInterface apiCommandInterface = new GetRequestApi(apiService);
            apiService.setApiCommandInterface(apiCommandInterface);
            useCase.setRequestInterface(apiService);

            ExcelService excelService = new ExcelService();
            ExcelFileGenerator excelFileGenerator = new ReportExcelGenerator(excelService);
            useCase.setExcelFileGenerator(excelFileGenerator);

            ReadConfigFile readConfigFile = new ReadJsonConfigFile();
            useCase.setReadConfigFile(readConfigFile);

            ThreadUtil threadUtil = new ThreadUtil();

            // Inyectar al ViewModel
            GenerateReportViewModel viewModel = new GenerateReportViewModel(useCase, threadUtil);
            viewModel.generateReport();
        });
    }
}
