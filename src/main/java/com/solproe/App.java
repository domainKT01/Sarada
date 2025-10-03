package com.solproe;

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
import com.solproe.ui.viewModels.GenerateReportViewModel;
import com.solproe.util.ThreadUtil;
import com.solproe.util.logging.LogInitializer;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        LogInitializer.init();

        boolean bool = Arrays.stream(args).toList().contains("--auto");
        if (bool) {
            GenerateReportUseCase useCase = getGenerateReportUseCase();

            ThreadUtil threadUtil = new ThreadUtil();
            // --- Fin: Bloque candidato para Factory o Inyección de Dependencias ---

            GenerateReportViewModel viewModel = new GenerateReportViewModel(useCase, threadUtil);
            viewModel.generateReportAsync(
                    () -> {
                        // Éxito → actualizar la UI en el hilo de JavaFX
                        System.out.println("view model callback success");
                    },
                    e -> {
                        // Error → mostrar alerta
                        System.out.println("view model callback failed");
                    }
            ); // Asumo que esto puede ser una tarea larga, considera ejecutar en background

        }
        else {
            MainApp mainApp = new MainApp();
            mainApp.exec();
        }
    }

    private static @NotNull GenerateReportUseCase getGenerateReportUseCase() {
        ApiCommandInvoker apiCommandInvoker = new ApiCommandInvoker();
        GenerateReportUseCase useCase = new GenerateReportUseCase();

        // Configuración de ApiService y sus dependencias
        ApiService apiService = new ApiService(apiCommandInvoker, useCase);
        apiCommandInvoker.setRequestInterface(apiService); // Inyección en Invoker
        ApiCommandInterface apiCommandInterface = new GetRequestApi(apiService);
        apiService.setApiCommandInterface(apiCommandInterface); // Inyección en ApiService

        useCase.setRequestInterface(apiService); // Inyección en UseCase

        // Configuración de ExcelService y sus dependencias
        ExcelService excelService = new ExcelService();
        ExcelFileGenerator excelFileGenerator = new ReportExcelGenerator(excelService);
        useCase.setExcelFileGenerator(excelFileGenerator);

        // Configuración de ReadConfigFile
        ReadConfigFile readConfigFile = new ReadJsonConfigFile();
        useCase.setReadConfigFile(readConfigFile);
        return useCase;
    }
}
