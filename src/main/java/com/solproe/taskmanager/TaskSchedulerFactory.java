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
}
