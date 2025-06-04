package com.solproe.util.logging;

import org.slf4j.LoggerFactory;

public class ErrorLogger {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ErrorLogger.class);

    public static void log(Throwable t) {
        logger.error("Error no controlado: ", t);
        System.out.println(t.getMessage());
    }

    public static void log(String message, Throwable t) {
        logger.error(message, t);
        System.out.println(t.getMessage());
    }

    public static void logInfo(String message) {
        logger.info(message);
    }
}
