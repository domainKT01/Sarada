package com.solproe.ui.viewModels;

import com.solproe.business.repository.ReportState;
import com.solproe.business.usecase.GenerateReportUseCase;
import com.solproe.util.ThreadUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

public class GenerateReportViewModel {

    private final GenerateReportUseCase useCase;
    private final ThreadUtil threadUtil;
    private final ObjectProperty<ReportState> state = new SimpleObjectProperty<>(new ReportState.Initial());

    public GenerateReportViewModel(GenerateReportUseCase useCase, ThreadUtil threadUtil) {
        this.useCase = useCase;
        this.threadUtil = threadUtil;
    }

    public void generateReport() {
        this.state.set(new ReportState.Loading());
        this.threadUtil.runAsync(() -> {
            useCase.generateRequestApi();
            this.state.set(new ReportState.Success("Reporte generado con éxito"));
        } , (e) -> {
            state.set(new ReportState.Error("Error al generar el reporte: " + e.getMessage()));
        });
    }

    public ObjectProperty<ReportState> stateProperty() {
        return state;
    }
}
