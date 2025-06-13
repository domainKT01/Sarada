package com.solproe.util.logging;

import com.solproe.util.OsInfo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogInitializer {

    public static void init() {
        Path logPath = null;
        String os = new OsInfo().getOsName();
        if (os.toLowerCase().contains("win")) {
            String appdata = System.getProperty("user.home") + File.separator + "AppData" + File.separator + "Roaming";
            logPath = Paths.get(appdata, ".Sarada", "logs");
            System.out.println("path win: " + logPath);
        } else if (os.toLowerCase().contains("mac")) {
            String userHome = new OsInfo().getUserHome();
            logPath = Paths.get(userHome, "Library", "Application Support", ".Sarada", "logs");
        } else if (os.toLowerCase().contains("nux") || os.toLowerCase().contains("nix") || os.toLowerCase().contains("aix")) {
            try {
                String userHome = System.getProperty("user.home");
                logPath = Paths.get(userHome, ".config", ".Sarada", "logs");
            }
            catch (Exception e) {
                ErrorLogger.log(e);
            }
        }

        if (logPath == null) {
            System.err.println("ERROR: No se pudo determinar la ruta de logs para el sistema operativo: " + os);
            // Si no se puede determinar la ruta, la aplicación no debería intentar crear directorios o establecer la propiedad.
            // Puedes lanzar una excepción RuntimeException o salir, dependiendo de la criticidad.
            throw new IllegalStateException("No se pudo inicializar la ruta de logs. SO no soportado o no detectado.");
        }

        try {
            // Files.createDirectories() es mejor que File.mkdir() porque crea todos los directorios padres si no existen.
            if (!Files.exists(logPath)) {
                try {
                    Files.createDirectories(logPath);
                    System.out.println("Directorio de logs creado: " + logPath);
                    logPath = logPath.resolve("app.log");
                    Files.createFile(logPath);
                    System.out.println("file de logs ");
                }
                catch (Exception e) {
                    System.out.println("error to create " + e.getMessage());
                }

            } else if (!Files.isDirectory(logPath)) {
                // Esto maneja el caso raro pero posible de que la ruta exista pero sea un archivo y no un directorio
                System.err.println("Error: La ruta de logs existe pero no es un directorio: " + logPath.toAbsolutePath());
                throw new IOException("La ruta de logs especificada no es un directorio.");
            } else {
                System.out.println("El directorio de logs ya existe: " + logPath.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Excepción al crear o verificar el directorio de logs: " + e.getMessage());
            // Si hay un error de IO (ej. permisos), la aplicación podría no funcionar correctamente.
            throw new RuntimeException("Fallo al inicializar el directorio de logs debido a un error de E/S.", e);
        }

        // --- Establecer la propiedad del sistema ---
        // Ahora logPath está garantizado de no ser null si llegamos aquí
        System.setProperty("log.path", logPath.toAbsolutePath().toString());
    }
}
