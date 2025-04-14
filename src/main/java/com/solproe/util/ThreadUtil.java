package com.solproe.util;

import javafx.concurrent.Task;

import java.util.function.Consumer;

public class ThreadUtil {
    public void runAsync(Runnable task, Consumer<Throwable> onError) {
        Task<Void> backgroundTask = new Task<>() {
            @Override
            protected Void call() {
                task.run();
                return null;
            }
        };

        backgroundTask.setOnFailed(e -> {
            Throwable error = backgroundTask.getException();
            if (error != null) onError.accept(error);
        });
        new Thread(backgroundTask).start();
    }
}

