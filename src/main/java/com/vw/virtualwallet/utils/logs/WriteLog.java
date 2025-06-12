package com.vw.virtualwallet.utils.logs;

/**
 * Utility class for logging messages with different styles.
 * Provides methods to log information and errors with specific color codes.
 *
 * @author caito
 */
public class WriteLog {
    private static final String COLOR_GREEN = "\u001B[32m";
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_YELLOW = "\u001B[33m";
    private static final String COLOR_RESET = "\u001B[0m";

    /**
     * Logs an informational message with yellow color.
     *
     * @param message the message to log
     * @return the formatted message with yellow color
     */
    public static String logInfo(String message) {
        return COLOR_GREEN + message + COLOR_RESET;
    }

    /**
     * Logs an error message with red color.
     *
     * @param message the message to log
     * @return the formatted message with red color
     */
    public static String logError(String message) {
        return COLOR_RED +message + COLOR_RESET;
    }

    /**
     * Logs a warning message with yellow color.
     *
     * @param message the message to log
     * @return the formatted message with yellow color
     */
    public static String logWarning(String message) {
        return COLOR_YELLOW + message +  COLOR_RESET;
    }
}
