package com.solproe.service.config;

import com.solproe.business.repository.ConfigPropertiesGeneratorInterface;
import com.solproe.util.logging.ErrorLogger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

public class ConfigPropertiesGenerator implements ConfigPropertiesGeneratorInterface {
    private String fileName;
    private String[] appConfigDirName; // Nombre del subdirectorio de tu app

    public ConfigPropertiesGenerator(String fileName, String... appConfigDirName) {
        this.fileName = fileName;
        if (appConfigDirName.length > 0) {
            this.appConfigDirName = appConfigDirName;
        }
    }

    public ConfigPropertiesGenerator() {}

    @Override
    public void setFilename(String filename) {
        this.fileName = filename;
    }

    @Override
    public void setDirName(String[] dirName) {
        this.appConfigDirName = dirName;
    }

    @Override
    public Path getAppConfigPath() {
        Path configDir = null;
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.toLowerCase().contains("win")) {
                System.out.println("so: " + os);
                // Para Windows: C:\Users\<Username>\AppData\Roaming\<YourApp>
                String appData = System.getenv("APPDATA");
                if (appData == null) { // Fallback si APPDATA no está configurado (raro)
                    configDir = Paths.get(System.getProperty("user.home"), "AppData", "Roaming",  "." + this.appConfigDirName[0], this.appConfigDirName.length > 1 ? this.appConfigDirName[1] : "");
                } else {
                    configDir = Paths.get(appData, "." + this.appConfigDirName[0], this.appConfigDirName.length > 1 ? this.appConfigDirName[1] : "");
                }
            } else if (os.toLowerCase().contains("nix") || os.toLowerCase().contains("nux") || os.toLowerCase().contains("aix")) {
                // Para Linux/macOS: /home/<Username>/.config/<YourApp> o /home/<Username>/.<YourApp>
                configDir = Paths.get(System.getProperty("user.home"), ".config", this.appConfigDirName[0], this.appConfigDirName.length > 1 ? this.appConfigDirName[1] : ""); // O ".config", depende de tu preferencia
            } else {
                // Fallback para otros OS, o si no se puede determinar
                configDir = Paths.get(System.getProperty("user.home"), this.appConfigDirName[0], this.appConfigDirName.length > 1 ? this.appConfigDirName[1] : "");
            }
        }
        catch (Exception e) {
            ErrorLogger.log("class ConfigPropertiesGenerator, directory: " + Arrays.toString(this.appConfigDirName), e);
            System.out.println("error");
        }
        assert configDir != null;
        return configDir.resolve(this.fileName); // Resuelve la ruta completa del archivo
    }


    public Path getAppDirPath() {
        Path configDir = null;
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                System.out.println("so: " + os);
                // Para Windows: C:\Users\<Username>\AppData\Roaming\<YourApp>
                String appData = System.getenv("APPDATA");
                if (appData == null) { // Fallback si APPDATA no está configurado (raro)
                    configDir = Paths.get(System.getProperty("user.home"), "AppData", "Roaming",  "." + this.appConfigDirName[0], this.appConfigDirName.length > 1 ? this.appConfigDirName[1] : "");
                } else {
                    configDir = Paths.get(appData, "." + this.appConfigDirName[0], this.appConfigDirName.length > 1 ? this.appConfigDirName[1] : "");
                }
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                // Para Linux/macOS: /home/<Username>/.config/<YourApp> o /home/<Username>/.<YourApp>
                configDir = Paths.get(System.getProperty("user.home"), ".config", this.appConfigDirName[0], this.appConfigDirName.length > 1 ? this.appConfigDirName[1] : ""); // O ".config", depende de tu preferencia
            } else {
                // Fallback para otros OS, o si no se puede determinar
                configDir = Paths.get(System.getProperty("user.home"), this.appConfigDirName[0], this.appConfigDirName.length > 1 ? this.appConfigDirName[1] : "");
            }
        }
        catch (Exception e) {
            ErrorLogger.log(String.valueOf(configDir), e);
            System.out.println("error");
        }
        return configDir;
    }

    // Tus métodos createPropertyFile y addProperties usarían getAppConfigPath()
    // en lugar de getFilePath() para obtener la ruta al archivo.
    // Ejemplo para createPropertyFile:
    @Override
    public boolean createPropertyFile() {
        Properties prop = new Properties();
        Path filePath = getAppConfigPath(); // Obtener la ruta en el directorio de usuario

        try (OutputStream outputStream = Files.newOutputStream(filePath)) { // Usa Files.newOutputStream para NIO.2
            prop.store(outputStream, "created properties file...");
            System.out.println("Archivo de propiedades creado en: " + filePath.toAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("properties exception: " + e.getMessage());
            throw new RuntimeException("Error al crear el archivo de propiedades: " + e.getMessage(), e);
        }
    }

    // Similarmente para addProperties...
    @Override
    public boolean addProperties(String[][] properties) {
        Properties prop = new Properties();
        Path filePath = getAppConfigPath();

        try (InputStream input = Files.newInputStream(filePath)) { // Usa Files.newInputStream para NIO.2
            prop.load(input);
        } catch (IOException e) {
            // Si el archivo no existe al intentar leer, es probable que sea la primera vez
            System.err.println("Advertencia: No se encontró el archivo de propiedades existente. Se creará uno nuevo si se añaden propiedades. " + e.getMessage());
        }

        for (String[] property : properties) {
            if (property[0] != null && property[1] != null) {
                prop.setProperty(property[0], property[1]);
            }
        }

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            prop.store(outputStream, "added properties successful...");
            System.out.println("Propiedades añadidas/actualizadas en: " + filePath.toAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.printf("add properties exception: " + e.getMessage());
            throw new RuntimeException("Error al añadir propiedades: " + e.getMessage(), e);
        }
    }
}
