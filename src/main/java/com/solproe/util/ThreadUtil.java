package com.solproe.util;

public class ThreadUtil {

    public static void runAsync(Runnable runnable) {
        Thread thread = new Thread(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                System.err.println("Error en ThreadUtil: " + e.getMessage());
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
