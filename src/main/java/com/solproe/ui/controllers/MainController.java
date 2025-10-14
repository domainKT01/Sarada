package com.solproe.ui.controllers;

import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.repository.ExcelFileGenerator;
import com.solproe.business.repository.ReadConfigFile;
import com.solproe.business.usecase.GenerateReportUseCase;
import com.solproe.service.APIs.ApiCommandInvoker;
import com.solproe.service.APIs.ApiService;
import com.solproe.service.APIs.GetRequestApi;
import com.solproe.service.APIs.whatsapp.WhatsappBusinessService;
import com.solproe.service.config.ReadJsonConfigFile;
import com.solproe.service.excel.ExcelService;
import com.solproe.service.excel.ReportExcelGenerator;
import com.solproe.ui.viewModels.ConfigFileViewModel; // Asumo que se usará más adelante
import com.solproe.ui.viewModels.GenerateReportViewModel;
import com.solproe.util.ThreadUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    // FXML Injections
    @FXML public Button challengesButton;
    @FXML public Button reportButton;
    @FXML public Button createCodeList;
    @FXML public Button createConfig;
    @FXML public Button dashboardButton;
    @FXML public BorderPane borderPane;

    // Controladores de las vistas hijas (si necesitas interactuar con ellos)
    private FormController formController; // Ejemplo, si create-file-config.fxml usa FormController
    // Podrías tener más campos para otros controladores si es necesario
    // private DashboardController dashboardController;
    // private ListCodeController listCodeController;
    // private MonthlyConfigController monthlyConfigController;

    // ViewModels (si son compartidos o gestionados por MainController)
    private ConfigFileViewModel configFileViewModel; // Inicializado pero no usado en el snippet original

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.configFileViewModel = new ConfigFileViewModel(); // Si se necesita globalmente

        setupNavigationButtons();
        setupReportButtonAction();

        // Cargar la vista inicial del dashboard
        loadViewIntoCenter("/views/dashboard.fxml", dashboardButton); // Pasa el botón para estilizarlo
    }

    /**
     * Configura las acciones para los botones de navegación principales.
     */
    private void setupNavigationButtons() {
        dashboardButton.setOnAction(_ -> loadViewAndStyleButton("/views/dashboard.fxml", dashboardButton));
        createConfig.setOnAction(_ -> {
            FXMLLoader loader = loadViewAndStyleButton("/create-file-config.fxml", createConfig);
            if (loader != null) {
                // Asumiendo que create-file-config.fxml tiene un FormController
                // y necesitas una referencia a él.
                this.formController = loader.getController();
                // Aquí podrías pasar 'configFileViewModel' al formController si lo necesita:
                // if (this.formController != null) {
                // this.formController.setConfigFileViewModel(this.configFileViewModel);
                // }
            }
        });
        createCodeList.setOnAction(_ -> loadViewAndStyleButton("/views/config/listCode/view.fxml", createCodeList));
        challengesButton.setOnAction(_ -> loadViewAndStyleButton("/views/config/monthly-config/monthly-file-config.fxml", challengesButton));
    }

    /**
     * Carga una vista FXML en el centro del BorderPane y actualiza el estilo del botón.
     *
     * @param fxmlPath Path al archivo FXML.
     * @param clickedButton El botón que disparó la acción.
     * @return FXMLLoader para acceder al controlador si es necesario, o null si falla la carga.
     */
    private FXMLLoader loadViewAndStyleButton(String fxmlPath, Button clickedButton) {
        FXMLLoader loader = loadViewIntoCenter(fxmlPath, clickedButton);
        return loader;
    }

    /**
     * Carga una vista FXML en el centro del BorderPane.
     *
     * @param fxmlPath Path al archivo FXML.
     * @return FXMLLoader para acceder al controlador si es necesario, o null si falla la carga.
     */
    private FXMLLoader loadViewIntoCenter(String fxmlPath, Button initialButton) {
        try {
            System.out.println("load view: " + fxmlPath);
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlPath), "No se pudo encontrar FXML: " + fxmlPath));
            Node view = loader.load();
            borderPane.setCenter(view);
            // currentCenterNode = view; // Si necesitas la referencia al nodo

            // Aquí podrías obtener el controlador si es genérico y necesitas interactuar con él.
            // Object childController = loader.getController();
            // if (childController instanceof SomeCommonInterface) {
            //     ((SomeCommonInterface) childController).setMainController(this);
            // }
            // O guardar referencias específicas como se hizo con formController.

            return loader;
        } catch (IOException e) {
            // Considera mostrar un diálogo de error al usuario aquí
            // throw new RuntimeException("Error al cargar la vista: " + fxmlPath, e); // O manejarlo más grácilmente
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Configura la acción para el botón de generar reporte.
     */
    private void setupReportButtonAction() {
        reportButton.setOnAction(_ -> generateReport());
    }

    /**
     * Orquesta la generación del reporte.
     * Considera mover la creación de dependencias a una clase Factory o usar Inyección de Dependencias.
     */
    private void generateReport() {
        try {
            // --- Inicio: Bloque candidato para Factory o Inyección de Dependencias ---
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

            //configuración del servicio de WhatsApp
            useCase.setWhatsappService(new WhatsappBusinessService());

            ThreadUtil threadUtil = new ThreadUtil();
            // --- Fin: Bloque candidato para Factory o Inyección de Dependencias ---

            GenerateReportViewModel viewModel = new GenerateReportViewModel(useCase, threadUtil);
            viewModel.generateReportAsync(
                    () -> {
                        // Éxito → actualizar la UI en el hilo de JavaFX
                        System.out.println("view model callback success");
                        javafx.application.Platform.runLater(() ->
                                showAlert(Alert.AlertType.INFORMATION, "Reporte generado", "El reporte fue generado correctamente.")
                        );
                    },
                    e -> {
                        // Error → mostrar alerta
                        System.out.println("view model callback failed");
                        javafx.application.Platform.runLater(() ->
                                showAlert(Alert.AlertType.ERROR, "Error", e.getCause().toString())
                        );
                    }
            ); // Asumo que esto puede ser una tarea larga, considera ejecutar en background

        } catch (Exception e) {
            // Mostrar error al usuario
        }
    }

    // --- Métodos para comunicación con controladores hijos (Ejemplos) ---

    /**
     * Permite a los controladores hijos obtener una referencia al ConfigFileViewModel.
     * @return la instancia de ConfigFileViewModel.
     */
    public ConfigFileViewModel getConfigFileViewModel() {
        return configFileViewModel;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}