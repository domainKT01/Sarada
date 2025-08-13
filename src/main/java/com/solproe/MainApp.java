package com.solproe;

import com.solproe.business.repository.ConfigPropertiesGeneratorInterface;
import com.solproe.service.config.ConfigPropertiesGenerator;
import com.solproe.taskmanager.ConfigTask;
import com.solproe.taskmanager.TaskManager;
import com.solproe.taskmanager.TaskScheduler;
import com.solproe.taskmanager.TaskSchedulerFactory;
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
        ValidateLoad validateLoad = new ValidateLoad("config.properties", ".Sarada");
        if (!validateLoad.validateFirstRun()) {
            TaskScheduler taskScheduler = TaskSchedulerFactory.getScheduler();
            String taskName = "autoGenerateExcelReport";
            String[] commands = {
                    ".Sarada"
            };

            String scheduleTime = "09:00"; // HH:MM
            try {
                //taskScheduler.scheduleTask(taskName, "Sarada", "DAILY", scheduleTime, commands);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
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