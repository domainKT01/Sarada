package com.solproe;

import com.solproe.taskmanager.TaskScheduler;
import com.solproe.taskmanager.TaskSchedulerFactory;
import com.solproe.util.ValidateLoad;
import com.solproe.util.logging.LogInitializer;
import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        LogInitializer.init();

        boolean bool = Arrays.stream(args).toList().contains("--auto");
        if (bool) {
            ValidateLoad validateLoad = new ValidateLoad("config.properties", "Sarada");
            if (!validateLoad.validateFirstRun()) {
                TaskScheduler taskScheduler = TaskSchedulerFactory.getScheduler();
                String taskName = "autoGenerateExcelReport";
                String[] commands = {
                        "Sarada",
                        "--auto",
                };

                String scheduleTime = "09:00"; // HH:MM
                try {
                    taskScheduler.scheduleTask(taskName, "Sarada", "DAILY", scheduleTime, commands);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            MainApp mainApp = new MainApp();
            mainApp.exec();
        }
    }
}
