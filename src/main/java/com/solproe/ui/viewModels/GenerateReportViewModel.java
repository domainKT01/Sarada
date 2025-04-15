package com.solproe.ui.viewModels;

import com.solproe.business.usecase.GenerateReportUseCase;
import com.solproe.util.ThreadUtil;
import javafx.concurrent.Task;

public class GenerateReportViewModel {

    private final GenerateReportUseCase useCase;

    public GenerateReportViewModel(GenerateReportUseCase useCase, ThreadUtil threadUtil) {
        this.useCase = useCase;
    }

    public void generateReport() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                useCase.generateRequestApi(); // Toda la lógica pesada está delegada
                return null;
            }
        };

        task.setOnFailed(e -> {
            Throwable error = task.getException();
            if (error != null) {
                error.printStackTrace();
                System.out.println("Error al generar el reporte: " + error.getMessage());
            }
        });

        ThreadUtil.runAsync(task);
    }
}
