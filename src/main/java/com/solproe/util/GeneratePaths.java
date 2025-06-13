package com.solproe.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GeneratePaths {
    private String fileName;
    private String dirName;


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public Path generateNewPath() {
        Path path;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.toLowerCase().contains("win")) {
            // Para Windows: C:\Users\<Username>\AppData\Roaming\<YourApp>
            String appData = System.getenv("APPDATA");
            if (appData == null) { // Fallback si APPDATA no está configurado (raro)
                path = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", this.dirName);
            } else {
                path = Paths.get(appData, "Roaming", this.dirName);
            }
        } else if (os.toLowerCase().contains("nix") || os.toLowerCase().contains("nux") || os.toLowerCase().contains("mac")) {
            // Para Linux/macOS: /home/<Username>/.config/<YourApp> o /home/<Username>/.<YourApp>
            path = Paths.get(System.getProperty("user.home"), ".config", "." + this.dirName); // O ".config", depende de tu preferencia
        } else {
            // Fallback para otros OS, o si no se puede determinar
            path = Paths.get(System.getProperty("user.home"), this.dirName);
        }
        System.out.println("path generated: " + path);
        return path.resolve(this.fileName);
    }
}
