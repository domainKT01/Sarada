package com.solproe.ui.viewModels;

import com.solproe.business.repository.ErrorCallback;
import com.solproe.business.repository.SuccessCallback;
import com.solproe.business.usecase.GenerateReportUseCase;
import com.solproe.util.ThreadUtil;
import javafx.concurrent.Task;

public class GenerateReportViewModel<T> {
    private final GenerateReportUseCase useCase;

    public GenerateReportViewModel(GenerateReportUseCase useCase, ThreadUtil threadUtil) {
        this.useCase = useCase;
    }

    public void generateReportAsync(SuccessCallback<Boolean> onSuccess, ErrorCallback onFailure) {
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() {
                var res = useCase.generateRequestApi();
                return res;
            }
        };

        task.setOnSucceeded(_ -> {
            Boolean res = task.getValue();
            onSuccess.onSuccess(res);
        });

        task.setOnFailed(_ -> {
            Throwable error = task.getException();
            System.out.println("Error al generar el reporte: " + (error != null ? error.getMessage() : "Error desconocido"));
            onFailure.onError(error);
        });

        ThreadUtil.runAsync(task);
    }
}
