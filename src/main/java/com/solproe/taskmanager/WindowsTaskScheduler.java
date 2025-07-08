package com.solproe.taskmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WindowsTaskScheduler implements TaskScheduler {
    private final String path;
    public String folder;

    public WindowsTaskScheduler() {
        this.path = "C:\\Program Files\\";
    }

    @Override
    public void scheduleTask(String taskName, String folder, String schedule, String time, String... commands) throws Exception {
        System.out.println("DEBUG: Scheduling task '" + taskName + "' on Windows...");
        this.folder = folder;
        // Ejemplo simplificado: "javaw.exe -jar \"C:\\path\\to\\app.jar\""
        // Asegúrate de que el 'command' incluya la ruta completa y argumentos
        StringBuilder commandToExecute = new StringBuilder(this.path + this.folder + "\\");
        commands[0] += ".exe";
        for (String command : commands) {
            commandToExecute.append(command);
            commandToExecute.append(" ");
        }
        String fullCommand = "\"" + commandToExecute + "\""; // Envuelve el comando en comillas

        String[] schtasksCommand = {
                "schtasks", "/create",
                "/tn", taskName,
                "/tr", fullCommand,
                "/sc", schedule, // Ej: DAILY, HOURLY
                "/st", time,     // Ej: 09:00
                "/sd", "01/01/2025", // Fecha de inicio fija, podrías hacer la dinámica
                "/f", // Forzar la creación (sobrescribe si existe)
                "/rl", "HIGHEST" // Ejecutar con los privilegios más altos
                // Para ejecutar sin usuario logueado, se necesita: "/ru", "SYSTEM" o "/ru", "user", "/rp", "pass"
        };

        executeCommand(schtasksCommand);
        System.out.println("INFO: Task '" + taskName + "' scheduled successfully on Windows.");
    }

    @Override
    public void deleteTask(String taskName) {

    }

    private void executeCommand(String[] command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true); // Redirige el error al output

        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("CMD OUTPUT: " + line);
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            // Manejo específico para el caso de que la tarea no exista al intentar borrar
            String output = getProcessOutput(process); // Puedes capturar el output para un mejor mensaje de error
            if (String.join(" ", command).contains("/delete") && output.contains("ERROR: The system cannot find the file specified")) {
                System.out.println("WARN: Task did not exist, no need to delete.");
            } else {
                throw new IOException("Command failed with exit code " + exitCode + ": " + String.join(" ", command) + "\nOutput: " + output);
            }
        }
    }

    private String getProcessOutput(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        return output.toString();
    }
}
