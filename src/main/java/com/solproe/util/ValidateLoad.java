package com.solproe.util;

import com.solproe.util.logging.ErrorLogger;

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
        if (osName.toLowerCase().contains("win")) {
            // Para Windows: C:\Users\<Username>\AppData\Roaming\<YourApp>
            configDir = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", this.appConfigDirName);
        } else if (osName.toLowerCase().contains("nix") || osName.toLowerCase().contains("nux") || osName.toLowerCase().contains("mac")) {
            // Para Linux/macOS: /home/<Username>/.config/<YourApp> o /home/<Username>/.<YourApp>
            configDir = Paths.get(System.getProperty("user.home"), ".config", this.appConfigDirName); // O ".config", depende de tu preferencia
            ErrorLogger.log("linux path: " + configDir, new Throwable());
        } else {
            // Fallback para otros OS, o si no se puede determinar
            configDir = Paths.get(System.getProperty("user.home"), this.appConfigDirName);
            ErrorLogger.log("another path: " + configDir, new Throwable());
        }

        System.out.println(Files.exists(configDir) + " dirPath: " + configDir);



        return Files.exists(configDir.resolve(this.fileName));
    }
}
