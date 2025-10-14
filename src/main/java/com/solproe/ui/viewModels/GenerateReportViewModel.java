package com.solproe.ui.viewModels;

import com.solproe.business.repository.ErrorCallback;
import com.solproe.business.repository.SuccessCallback;
import com.solproe.business.usecase.GenerateReportUseCase;
import com.solproe.service.APIs.whatsapp.WhatsappBusinessService;
import com.solproe.util.ThreadUtil;
import javafx.concurrent.Task;

import java.util.concurrent.*;

public class GenerateReportViewModel {
    private final GenerateReportUseCase useCase;

    public GenerateReportViewModel(GenerateReportUseCase useCase, ThreadUtil threadUtil) {
        this.useCase = useCase;
    }

    public void generateReportAsync(SuccessCallback onSuccess, ErrorCallback onFailure) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Callable<Boolean> tarea = this.useCase::generateRequestApi;
        this.useCase.setWhatsappService(new WhatsappBusinessService());

        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
                return useCase.generateRequestApi();
            }
        };

        Future<Boolean> future = executorService.submit(tarea);

        try {
            Boolean res = future.get();
            onSuccess.onSuccess();
            if (res) {
                onSuccess.onSuccess();
            }
            else {
                onFailure.onError(new Throwable("falló al generar el reporte"));
            }
            executorService.shutdown();
        } catch (ExecutionException | InterruptedException e) {
            onFailure.onError(e);
            executorService.shutdown();
        }

        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }

        /*
        task.setOnSucceeded(_ -> {
            Boolean res = task.getValue();
            onSuccess.onSuccess();
        });

        task.setOnFailed(_ -> {
            Throwable error = task.getException();
            System.out.println("Error al generar el reporte: " + (error != null ? error.getMessage() : "Error desconocido"));
            onFailure.onError(error);
        });

        ThreadUtil.runAsync(task);
        */
    }
}
