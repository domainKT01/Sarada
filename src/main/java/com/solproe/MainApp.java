package com.solproe;

import com.solproe.business.repository.ConfigPropertiesGeneratorInterface;
import com.solproe.service.config.ConfigPropertiesGenerator;
import com.solproe.taskmanager.ConfigTask;
import com.solproe.taskmanager.TaskManager;
import com.solproe.util.OsInfo;
import com.solproe.util.ValidateLoad;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ValidateLoad validateLoad = new ValidateLoad("config.properties", "Sarada");
        String path;
        String directory;
        if (new OsInfo().getOsName().toLowerCase().contains("win")) {
            path = "C:\\Program Files\\Sarada\\sarada.exe";
            directory = "C:\\Program Files\\Sarada";
        }
        else {
            path = "/opt/saradaapp/bin/SaradaApp";
            directory = "/opt/saraapp";
        }
        if (!validateLoad.validateFirstRun()) {
            TaskManager taskManager = new TaskManager();
            taskManager.programTask(new ConfigTask(
                    "automatic task",
                    path,
                    "--auto",
                    "09:30",
                    "DAILY",
                    directory
            ));
            String[] dirName = {
                    "Sarada"
            };
            ConfigPropertiesGeneratorInterface config = new ConfigPropertiesGenerator("config.properties", dirName);
            boolean bool = config.createPropertyFile();
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/main-view.fxml"))); // No leading /
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Sarada App");
        stage.show();
    }

    public void exec() {
        launch();
    }
}