package com.solproe.ui.controllers;

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
import com.solproe.ui.viewModels.ConfigFileViewModel; // Asumo que se usará más adelante
import com.solproe.ui.viewModels.GenerateReportViewModel;
import com.solproe.util.ThreadUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import org.slf4j.Logger; // Importa un Logger
import org.slf4j.LoggerFactory; // Importa un LoggerFactory

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // Logger para la clase
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    // Constantes para estilos de fuente (DRY)
    private static final Font FONT_NORMAL = new Font("System", 13.0F);
    private static final Font FONT_SELECTED = new Font("Cursive", 18F); // Considera usar CSS para esto

    // FXML Injections
    @FXML public Button challengesButton;
    @FXML public Button reportButton;
    @FXML public Button createCodeList;
    @FXML public Button createConfig;
    @FXML public Button dashboardButton;
    @FXML public BorderPane borderPane;

    private Button lastClickedButton;
    // private Node currentCenterNode; // Reemplaza a borderPaneConf si solo guardas el nodo

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
        updateButtonStyles(clickedButton);
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
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlPath), "No se pudo encontrar FXML: " + fxmlPath));
            Node view = loader.load();
            borderPane.setCenter(view);
            // currentCenterNode = view; // Si necesitas la referencia al nodo

            // Si se proporciona un botón inicial (ej. carga del dashboard), estilízalo.
            if (initialButton != null && lastClickedButton == null) {
                updateButtonStyles(initialButton);
            }

            // Aquí podrías obtener el controlador si es genérico y necesitas interactuar con él.
            // Object childController = loader.getController();
            // if (childController instanceof SomeCommonInterface) {
            //     ((SomeCommonInterface) childController).setMainController(this);
            // }
            // O guardar referencias específicas como se hizo con formController.

            logger.info("Vista '{}' cargada en el centro.", fxmlPath);
            return loader;
        } catch (IOException e) {
            logger.error("Error al cargar la vista FXML: " + fxmlPath, e);
            // Considera mostrar un diálogo de error al usuario aquí
            // throw new RuntimeException("Error al cargar la vista: " + fxmlPath, e); // O manejarlo más grácilmente
            return null;
        } catch (NullPointerException e) {
            logger.error("Ruta FXML nula o recurso no encontrado: " + fxmlPath, e);
            return null;
        }
    }

    /**
     * Actualiza los estilos de fuente de los botones de navegación.
     * El botón clickeado actualmente se resalta, y el anterior vuelve a la normalidad.
     *
     * @param clickedButton El botón que acaba de ser presionado.
     */
    private void updateButtonStyles(Button clickedButton) {
        if (lastClickedButton != null) {
            lastClickedButton.setFont(FONT_NORMAL);
        }
        if (clickedButton != null) {
            clickedButton.setFont(FONT_SELECTED);
            lastClickedButton = clickedButton;
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
        logger.info("Iniciando generación de reporte...");
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

            ThreadUtil threadUtil = new ThreadUtil();
            // --- Fin: Bloque candidato para Factory o Inyección de Dependencias ---

            GenerateReportViewModel viewModel = new GenerateReportViewModel(useCase, threadUtil);
            viewModel.generateReport(); // Asumo que esto puede ser una tarea larga, considera ejecutar en background
            logger.info("Proceso de generación de reporte iniciado por ViewModel.");

        } catch (Exception e) {
            logger.error("Error durante la generación del reporte.", e);
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

    // Si los controladores hijos necesitan llamar métodos en MainController:
    // public void notifyMainController(String message) {
    //     logger.info("Notificación recibida de un controlador hijo: {}", message);
    // }
}