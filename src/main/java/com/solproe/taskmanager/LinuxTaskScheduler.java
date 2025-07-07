package com.solproe.taskmanager;

import java.io.*;

public class LinuxTaskScheduler implements TaskScheduler {

    @Override
    public void scheduleTask(String taskName, String command, String schedule, String time) throws Exception {
        System.out.println("DEBUG: Scheduling task '" + taskName + "' on Linux (using cron)...");
        // Ejemplo de comando cron: "0 9 * * * /usr/bin/java -jar /path/to/app.jar"
        // 'schedule' aquí podría ser una expresión cron completa, o procesarse a partir de 'time'

        // Formato cron para la hora: "MM HH * * *"
        String cronTime = time.split(":")[1] + " " + time.split(":")[0]; // Mantiene el formato básico HH:MM -> MM HH
        String cronEntry = cronTime + " * * * " + command + " # " + taskName; // Añade un comentario con el nombre de la tarea

        // Obtener la crontab actual, añadir la nueva entrada, y actualizar
        String existingCrontab = executeCommand("crontab", "-l");
        if (existingCrontab == null || existingCrontab.contains("no crontab for")) {
            existingCrontab = ""; // No hay crontab existente
        }

        // Eliminar entrada existente si ya existe
        StringBuilder newCrontabContent = new StringBuilder();
        boolean taskExists = false;
        for (String line : existingCrontab.split("\n")) {
            if (!line.contains("# " + taskName)) { // Si la línea no es nuestra tarea
                newCrontabContent.append(line).append("\n");
            } else {
                taskExists = true;
            }
        }
        newCrontabContent.append(cronEntry).append("\n"); // Añadir la nueva o actualizada entrada

        executeCommandWithInput("crontab", "-", newCrontabContent.toString()); // Usar "-" para leer desde stdin
        System.out.println("INFO: Task '" + taskName + "' scheduled successfully on Linux.");
    }

    @Override
    public void deleteTask(String taskName) throws Exception {
        System.out.println("DEBUG: Deleting task '" + taskName + "' on Linux (using cron)...");
        String existingCrontab = executeCommand("crontab", "-l");
        if (existingCrontab == null || existingCrontab.contains("no crontab for")) {
            System.out.println("WARN: No crontab found for user, task '" + taskName + "' cannot be deleted.");
            return;
        }

        StringBuilder newCrontabContent = new StringBuilder();
        boolean taskFound = false;
        for (String line : existingCrontab.split("\n")) {
            if (!line.contains("# " + taskName)) {
                newCrontabContent.append(line).append("\n");
            } else {
                taskFound = true;
            }
        }

        if (taskFound) {
            executeCommandWithInput("crontab", "-", newCrontabContent.toString());
            System.out.println("INFO: Task '" + taskName + "' deleted successfully from Linux.");
        } else {
            System.out.println("WARN: Task '" + taskName + "' not found in crontab, no need to delete.");
        }
    }

    private String executeCommand(String... command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true); // Redirige el error al output
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Command failed with exit code " + exitCode + ": " + String.join(" ", command) + "\nOutput: " + output.toString());
        }
        return output.toString();
    }

    private void executeCommandWithInput(String command, String arg, String input) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command, arg);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Escribir la entrada al proceso (crontab - por ejemplo)
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(input);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("CRONTAB CMD OUTPUT: " + line);
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Command with input failed with exit code " + exitCode + ": " + command + " " + arg);
        }
    }
}
