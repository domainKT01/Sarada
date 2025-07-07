package com.solproe.taskmanager;

import java.io.IOException;

public class TaskManager {
    private final CommandShTask commandShTask;


    public TaskManager() {
        this.commandShTask = new CommandShTask();
    }

    /**
     * Programa una nueva tarea en el Programador de Tareas de Windows.
     * Si la tarea ya existe, la sobrescribe.
     *
     * @param configTask La configuración de la tarea a programar.
     * @throws RuntimeException Sí ocurre un error durante la programación.
     */

    public void programTask(ConfigTask configTask) {
        try {
            String[] deleteCommand = configTask.getDeleteCommand();
            try {
                commandShTask.execute(deleteCommand);
                Thread.sleep(1200);
            } catch (IOException | InterruptedException e) {
                if (!e.getMessage().contains("ERROR: The system cannot find the file specified") &&
                        !e.getMessage().contains("ERROR: The specified task name does not exist")) {

                    throw new RuntimeException(e);
                }
            }

            String[] createCommand = configTask.getCreationCommand();
            commandShTask.execute(createCommand);
        }
        catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
