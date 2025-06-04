package com.solproe.util.logging;

import com.solproe.util.OsInfo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogInitializer {


    public static void init() {
        Path logPath = null;
        String os = new OsInfo().getOsName();

        if (os.contains("win")) {
            String appdata = System.getenv("APPDATA");
            logPath = Paths.get(appdata, ".Sarada", "logs");
        } else if (os.contains("mac")) {
            String userHome = new OsInfo().getUserHome();
            logPath = Paths.get(userHome, "Library", "Application Support", ".Sarada", "logs");
        } else if (os.contains("nux") || os.contains("nix") || os.contains("aix")) {
            String userHome = System.getProperty("user.home");
            logPath = Paths.get(userHome, ".config", ".Sarada", "logs");
        }

        File logDir = logPath.toFile();
        if (!logDir.exists()) {
            try {
                logDir.mkdir();
                System.out.println("dir created...");
            } catch (Exception e) {
                System.out.println("exception in LogInitializer: " + e.getMessage());
            }
        }

        System.setProperty("log.path", logPath.toAbsolutePath().toString());
        System.out.println("property log.path: " + System.getProperty("log.path"));
    }
}
