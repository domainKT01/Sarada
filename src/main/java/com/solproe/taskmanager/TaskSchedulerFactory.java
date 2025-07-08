package com.solproe.taskmanager;

public class TaskSchedulerFactory {

    public static TaskScheduler getScheduler() {
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println("INFO: Detected OS: " + osName);

        if (osName.toLowerCase().contains("win")) {
            return new WindowsTaskScheduler();
        } else if (osName.toLowerCase().contains("nix") || osName.toLowerCase().contains("nux")) {
            return new LinuxTaskScheduler();
        } else {
            throw new UnsupportedOperationException("Sistema operativo no soportado para la programación de tareas: " + osName);
        }
    }

    // --- Uso del Factory Method en el Código Cliente ---
    public static void main(String[] args) {
        try {
            // El cliente simplemente pide un planificador, sin saber cuál obtiene
            TaskScheduler scheduler = TaskSchedulerFactory.getScheduler();

            String taskName = "autoGenerateExcelReport";
            String commandToExecute;
            String scheduleFrequency; // Para schtasks
            String scheduleTime = "09:00"; // HH:MM

            // Ajusta el comando según el sistema operativo esperado
            // En cron, esto se maneja con la expresión: "0 9 * * *"
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                commandToExecute = "C:\\Program Files\\Sarada\\Sarada.exe --auto";
            } else {
                commandToExecute = "/usr/bin/java -jar /ruta/a/tu/MiAplicacionJava.jar";
                // Para Linux (cron), el 'schedule' podría ser una expresión cron completa o solo la hora
                // Nota: El método scheduleTask de LinuxTaskScheduler simplifica esto a partir de time y el comando
            }
            scheduleFrequency = "DAILY"; // Para Windows: DAILY, HOURLY, WEEKLY, etc.

            // Programar la tarea
            //scheduler.scheduleTask(taskName, commandToExecute, scheduleFrequency, scheduleTime);

            System.out.println("\nTarea programada con éxito. Espera unos segundos para verificar...");
            // Puedes añadir aquí lógica para verificar si la tarea se creó correctamente
            // (ej. en Windows, buscar en el Programador de Tareas; en Linux, 'crontab -l')

            // Ejemplo de cómo eliminar la tarea después de un tiempo (para probar)
            // System.out.println("\nEsperando 10 segundos para eliminar la tarea...");
            // Thread.sleep(10000); // Espera 10 segundos
            // scheduler.deleteTask(taskName);
            // System.out.println("Tarea eliminada con éxito.");

        } catch (UnsupportedOperationException e) {
            System.err.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Se produjo un error al programar la tarea: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
