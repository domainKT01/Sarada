package com.solproe.taskmanager;

import com.solproe.util.OsInfo;
import org.jetbrains.annotations.NotNull;

public class ConfigTask {

    private final String name;
    private String description;
    private final String programPath;
    private final String programArgs;
    private final String executionTime; // HH:MM
    private final String timesQuantity;    // DAILY, WEEKLY, etc.
    private boolean executeLogless;
    private String executionUser;
    private String executionPassword;
    private String jobDirectory; // Nuevo parámetro para el directorio de trabajo

    public ConfigTask(String name, String programPath, String programArgs,
                              String executionTime, String timesQuantity, String jobDirectory) {
        if (name == null || name.trim().isEmpty() ||
                programPath == null || programPath.trim().isEmpty() ||
                executionTime == null || executionTime.trim().isEmpty() ||
                timesQuantity == null || timesQuantity.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre, ruta del programa, hora de ejecución y frecuencia son obligatorios.");
        }
        this.name = name;
        this.programPath = programPath;
        this.programArgs = programArgs != null ? programArgs : "";
        this.executionTime = executionTime;
        this.timesQuantity = timesQuantity;
        this.jobDirectory = jobDirectory; // Asignar el directorio de trabajo
        this.description = "Tarea programada automáticamente.";
        this.executeLogless = false; // Por defecto, no ejecutar sin login
        this.executionUser = null;
        this.executionPassword = null;
    }

    // Métodos para configurar opciones adicionales (chaining)
    public ConfigTask withDescription(String description) {
        this.description = description;
        return this;
    }

    public ConfigTask executeLogless(String user, String password) {
        this.executeLogless = true;
        this.executionUser = user;
        this.executionPassword = password;
        return this;
    }

    public String[] getCreationCommand() {
        // Construye el comando schtasks.exe
        // Nota: schtasks no tiene un parámetro directo para el directorio de trabajo.
        // La mejor práctica es que el 'rutaPrograma' sea un .bat que cambie el dir.
        // Este ejemplo simplifica, asumiendo que el programa manejara las rutas relativas.
        // O el directorio de trabajo ya es el de 'rutaPrograma'.

        String slash = new OsInfo().getOsName().contains("win") ? "\"" : "/";

        String[] comandoBase = getComandoBase(slash);

        // Si se debe ejecutar sin login
        if (this.executeLogless) {
            String[] commandLogless = new String[comandoBase.length + 4]; // +4 para /ru, usuario, /rp, contraseña
            System.arraycopy(comandoBase, 0, commandLogless, 0, comandoBase.length);
            commandLogless[comandoBase.length] = "/ru";
            commandLogless[comandoBase.length + 1] = this.executionUser;
            commandLogless[comandoBase.length + 2] = "/rp";
            commandLogless[comandoBase.length + 3] = this.executionPassword;
            return commandLogless;
        } else {
            return comandoBase;
        }
    }

    private String @NotNull [] getComandoBase(String slash) {
        String completedProgram = slash + this.programPath + slash;
        if (!this.programArgs.isEmpty()) {
            completedProgram += " " + this.programArgs;
        }

        // Comando base para crear la tarea
        // Fecha de inicio arbitraria, se podría hacer dinámica
        // Sobrescribir si la tarea ya existe
        // Ejecutar con los privilegios más altos
        return new String[]{
                "schtasks", "/create",
                "/tn", name,
                "/tr", completedProgram,
                "/sc", timesQuantity,
                "/st", executionTime,
                "/sd", "01/01/2025", // Fecha de inicio arbitraria, se podría hacer dinámica
                "/f", // Sobrescribir si la tarea ya existe
                "/rl", "HIGHEST" // Ejecutar con los privilegios más altos
        };
    }

    public String[] getDeleteCommand() {
        return new String[] {"schtasks", "/delete", "/tn", this.name, "/f"};
    }

    public String getName() {
        return this.name;
    }
}
