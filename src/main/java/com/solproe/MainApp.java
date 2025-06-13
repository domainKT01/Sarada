package com.solproe;

import com.solproe.business.repository.ConfigPropertiesGeneratorInterface;
import com.solproe.service.config.ConfigPropertiesGenerator;
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
        if (!validateLoad.validateFirstRun()) {
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