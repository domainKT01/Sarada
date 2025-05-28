package com.solproe.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ValidateLoad {
    private final String appConfigDirName;
    private String fileName;


    public ValidateLoad(String fileName, String appConfigDirName) {
        this.fileName = fileName;
        this.appConfigDirName = appConfigDirName;
    }

    public boolean validateFirstRun() {
        Path configDir;
        String osName = new OsInfo().getOsName();
        if (osName.contains("win")) {
            // Para Windows: C:\Users\<Username>\AppData\Roaming\<YourApp>
            String appData = System.getenv("APPDATA");
            if (appData == null) { // Fallback si APPDATA no está configurado (raro)
                configDir = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", this.appConfigDirName);
            } else {
                configDir = Paths.get(appData, this.appConfigDirName);
            }
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
            // Para Linux/macOS: /home/<Username>/.config/<YourApp> o /home/<Username>/.<YourApp>
            configDir = Paths.get(System.getProperty("user.home"), ".config", "." + this.appConfigDirName); // O ".config", depende de tu preferencia
        } else {
            // Fallback para otros OS, o si no se puede determinar
            configDir = Paths.get(System.getProperty("user.home"), this.appConfigDirName);
        }

        return Files.exists(configDir);
    }
}
