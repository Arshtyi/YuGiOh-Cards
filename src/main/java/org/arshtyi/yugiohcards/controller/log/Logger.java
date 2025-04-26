package org.arshtyi.yugiohcards.controller.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for basic logging operations. Provides static methods to log messages to console or
 * file with severity levels and color-coding for console output. This class should not be
 * instantiated.
 *
 * @throws UnsupportedOperationException if an attempt is made to instantiate this utility class.
 */
public class Logger { // Renamed class from logger to Logger
    /** This constructor is private to prevent instantiation of the Logger utility class. */
    private Logger() { // Updated constructor name to match class name
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated");
    }

    // ANSI color codes for console output
    /** Reset console color */
    private static final String ANSI_RESET = "\u001B[0m";

    /** Red color for errors */
    private static final String ANSI_RED = "\u001B[31m";

    /** Green color for info */
    private static final String ANSI_GREEN = "\u001B[32m";

    /** Yellow color for warnings */
    private static final String ANSI_YELLOW = "\u001B[33m";

    /** Cyan color for debug */
    private static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Logs a message to a file or console with the specified severity level. Includes class name,
     * method name, and current time in the log message. Handles exceptions that may occur during
     * file writing. Console output is color-coded based on the log level.
     *
     * @param level The severity level of the log message (e.g., INFO, ERROR, WARNING, DEBUG).
     * @param message The log message to be written.
     * @param sourceClass The class from which the log message originates.
     * @param filePath The path to the log file. If null or empty, logs to the console.
     */
    public static void log(String level, String message, Class<?> sourceClass, String filePath) {
        /** Get the current time and format it as "yyyy-MM-dd -- HH:mm:ss.SSS". */
        String timestamp =
                LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd -- HH:mm:ss.SSS"));
        /**
         * Format the log message to include the timestamp, severity level, class name, method name,
         * package name, and the log message itself.
         */
        String logMessage =
                String.format(
                        "%s  %s  -- [%s.%s@%s] : %s",
                        timestamp,
                        level,
                        sourceClass.getSimpleName(),
                        Thread.currentThread().getStackTrace()[2].getMethodName(),
                        sourceClass.getPackage().getName(),
                        message);
        // Get the appropriate color based on log level
        String color;
        switch (level.toUpperCase()) {
            case "INFO":
                color = ANSI_GREEN;
                break;
            case "ERROR":
                color = ANSI_RED;
                break;
            case "WARNING":
                color = ANSI_YELLOW;
                break;
            case "DEBUG":
                color = ANSI_CYAN;
                break;
            default:
                color = ANSI_RESET;
        }

        // Print to console if filePath is null or empty
        if (filePath == null || filePath.isEmpty()) {
            System.out.println(color + logMessage + ANSI_RESET);
            return;
        }

        // Ensure the log file exists
        try {
            File logFile = new File(filePath);
            File parentDir = logFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs(); // Create parent directories if they don't exist
            }
            if (!logFile.exists()) {
                logFile.createNewFile(); // Create the file if it doesn't exist
            }
        } catch (IOException e) {
            System.err.println("Failed to create log file: " + e.getMessage());
            // Optionally, log to console as a fallback
            System.out.println(color + logMessage + ANSI_RESET);
            return; // Stop further processing if file creation fails
        }

        // Write to file
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(logMessage + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            System.err.println("Logging failed: " + e.getMessage());
        }
    }
}
