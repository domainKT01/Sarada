package com.solproe.util.logging;

import com.solproe.service.config.ConfigPropertiesGenerator;
import com.solproe.util.OsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ErrorLogger {
    private static final Logger logger = LoggerFactory.getLogger(ErrorLogger.class);

    public static void log(Throwable t) {
        try {
            ErrorLogger.create(t);
        } catch (Exception e) {
            System.out.println("problema al crear el log");
            throw new RuntimeException(e);
        }
        System.out.println(t.getMessage());
    }

    public static void log(String message, Throwable t) {
        try {
            ErrorLogger.create(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(t.getMessage());
    }

    public static void logInfo(String message) {

    }

    private static void create(Throwable t, String... msg) {
        Path path = new ConfigPropertiesGenerator("app.log", "Sarada").getAppConfigPath();
        File file = new File(path.toUri());
        if (Files.exists(path)) {
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                if (msg.length > 0) {
                    fileWriter.write(t.getMessage() + msg[0] + "\r\n");
                } else {
                    fileWriter.write(t.getMessage() + "\r\n");
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + " método create ErrorLog");
            }
        }
        else {
            try {
                Files.createFile(path);
                try (FileWriter fileWriter = new FileWriter(file, true)) {
                    fileWriter.write(t.getMessage() + "\r\n");
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
