package com.solproe.ui.viewModels;

import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.repository.ExcelFileGenerator;
import com.solproe.business.repository.ReadConfigFile;
import com.solproe.business.usecase.GenerateReportUseCase;
import com.solproe.service.APIs.ApiCommandInvoker;
import com.solproe.service.APIs.ApiService;
import com.solproe.service.APIs.GetRequestApi;
import com.solproe.service.config.ReadJsonConfigFile;
import com.solproe.service.excel.ExcelService;
import com.solproe.service.excel.ReportExcelGenerator;
import javafx.concurrent.Task;

public class GenerateReportViewModel {

    public void generateReport() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    ApiCommandInvoker apiCommandInvoker = new ApiCommandInvoker();
                    GenerateReportUseCase generateReportUseCase = new GenerateReportUseCase();
                    ApiService apiService = new ApiService(apiCommandInvoker, generateReportUseCase);
                    apiCommandInvoker.setRequestInterface(apiService);
                    ApiCommandInterface apiCommandInterface = new GetRequestApi(apiService);
                    apiService.setApiCommandInterface(apiCommandInterface);
                    generateReportUseCase.setRequestInterface(apiService);
                    ExcelService excelService = new ExcelService();
                    ExcelFileGenerator excelFileGenerator = new ReportExcelGenerator(excelService);
                    generateReportUseCase.setExcelFileGenerator(excelFileGenerator);
                    ReadConfigFile readConfigFile = new ReadJsonConfigFile();
                    generateReportUseCase.setReadConfigFile(readConfigFile);
                    generateReportUseCase.generateRequestApi();
                }
                catch (Exception e) {
                    System.out.println("thread exc: " + e.getMessage());
                }
                return null;
            }
        };

        task.setOnFailed(event -> {
            Throwable error = task.getException();
            if (error != null) {
                error.printStackTrace();
                System.out.println("error: " + error.getMessage());
            }
        });

        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.err.println("Excepción no capturada en el hilo: " + e.getMessage());
            e.printStackTrace();
        });
        thread.start();
    }
}
