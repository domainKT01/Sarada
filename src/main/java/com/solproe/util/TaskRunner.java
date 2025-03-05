package com.solproe.util;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRunner {
    private static final ExecutorService executor = Executors.newFixedThreadPool(1); // Pool de hilos


    public static <V> void runAsync(Task<V> task, Runnable onSuccess, Runnable onError) {
        executor.submit(task); // Ejecutar la tarea en segundo plano
        task.setOnSucceeded(e -> Platform.runLater(onSuccess));  // Ejecutar en el hilo de JavaFX si éxito
        task.setOnFailed(e -> Platform.runLater(onError));  // Ejecutar en el hilo de JavaFX si falla
    }

    public static void shutdown() {
        executor.shutdown();
    }
}
